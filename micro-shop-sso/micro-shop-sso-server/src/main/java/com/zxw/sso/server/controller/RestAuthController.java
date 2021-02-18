package com.zxw.sso.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.zxw.common.web.web.JSONResponse;
import com.zxw.sso.server.config.oauth.RequestFactory;
import com.zxw.sso.server.service.LoginService;
import com.zxw.sso.server.vo.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuhongyun
 * @date 2020/6/1 13:34
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class RestAuthController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/render/{source}")
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        log.info("进入render：" + source);
        AuthRequest authRequest = RequestFactory.getInstance(source).getRequest();
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info(authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    /**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:8443/oauth/callback/github
     */
    @RequestMapping("/callback/{source}")
    public JSONResponse login(@PathVariable("source") String source, AuthCallback callback) throws Exception {
        log.info("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        TokenInfo response = loginService.login(null,source,callback);
        log.info(JSONObject.toJSONString(response));
        return JSONResponse.succeed(response);
    }

    @RequestMapping("/revoke/{source}/{token}")
    public Object revokeAuth(@PathVariable("source") String source, @PathVariable("token") String token) throws IOException {
        AuthRequest authRequest = RequestFactory.getInstance(source).getRequest();
        return authRequest.revoke(AuthToken.builder().accessToken(token).build());
    }

    @RequestMapping("/refresh/{source}")
    public Object refreshAuth(@PathVariable("source") String source, String token){
        AuthRequest authRequest = RequestFactory.getInstance(source).getRequest();
        return authRequest.refresh(AuthToken.builder().refreshToken(token).build());
    }

}