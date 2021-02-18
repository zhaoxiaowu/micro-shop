package com.zxw.sso.server.service.impl;

import com.zxw.common.redis.RedisManager;
import com.zxw.common.web.exception.Exceptions;
import com.zxw.common.web.utils.HttpContextUtils;
import com.zxw.sso.sdk.SsoConstant;
import com.zxw.sso.sdk.SsoErrorCode;
import com.zxw.sso.sdk.TokenHelper;
import com.zxw.sso.sdk.TokenUserData;
import com.zxw.sso.server.config.oauth.RequestFactory;
import com.zxw.sso.server.service.LoginService;
import com.zxw.sso.server.vo.TokenInfo;
import com.zxw.sso.server.vo.UserForm;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author wuhongyun
 * @date 2020/5/26 16:59
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private TokenHelper tokenHelper;

    @Value("${sys_jwt_expiration:3600}")
    private Long expiration;

    @Override
    public TokenInfo login(UserForm user, String source, AuthCallback callback) throws Exception {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String redirectUrl = request.getParameter("redirectUrl");
        TokenInfo info = new TokenInfo();
        TokenUserData data = new TokenUserData();
        data.setSource(source);
        if (!SsoConstant.SOURCE_WEB.equals(source)) {
            AuthRequest authRequest = RequestFactory.getInstance(source).getRequest();
            AuthResponse authResponse = authRequest.login(callback);
            if (authResponse.ok()) {
                AuthUser authUser = (AuthUser) authResponse.getData();
                data.setUsername(authUser.getUsername());
                data.setAvatar(authUser.getAvatar());
                //TODO 跟新数据库数据 使用线程异步更新数据库数据
//                User newUser = BeanConvertUtil.doConvert(authUser, User.class);
//                newUser.setSource(authUser.getSource().toString());
//                if (null != authUser.getGender()) {
//                    newUser.setGender(authUser.getGender().getCode());
//                }
//                User user = userService.getByUuidAndSource(authUser.getUuid(), authUser.getSource().toString());
//                newUser.setUserType(UserTypeEnum.USER);
//                if (null != user) {
//                    newUser.setId(user.getId());
//                    userService.updateSelective(newUser);
//                } else {
//                    userService.insert(newUser);
//                }
            } else {
                throw Exceptions.throwBusinessException(SsoErrorCode.THIRD_AUTH_FAILED);
            }
        } else {
            if (("admin").equals(user.getUsername()) && ("123456").equals(user.getPassword())) {
                // TODO 数据库查询
                data.setUsername(user.getUsername());
                data.setAvatar("avatar");
            } else {
                throw Exceptions.throwBusinessException(SsoErrorCode.LOGIN_FAILED);
            }

        }
        String jid = UUID.randomUUID().toString().replace("-", "");
        data.setJwtId(jid);
        redisManager.set(SsoConstant.TOKEN_USER_DATA + data.getUsername(), data, expiration);
        Map<String, Object> claims = new HashMap<>();
        claims.put(SsoConstant.CLAIM_KEY_JWT_ID, jid);
        if (isValidUrl(redirectUrl)) {
            info.setRedirectUrl(redirectUrl);
        }
        String jwt = tokenHelper.generateToken(claims);
        info.setJwt(jwt);
        return info;
    }

    private boolean isValidUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        return Pattern.matches(PATTERN_URL, url);
    }

    /** 重定向地址正则 */
    private static final String PATTERN_URL = "(http|https)?://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
}
