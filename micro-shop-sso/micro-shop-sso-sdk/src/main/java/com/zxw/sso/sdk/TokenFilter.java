package com.zxw.sso.sdk;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zxw.common.redis.RedisManager;
import com.zxw.common.web.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * token过滤器
 *
 * @author wuhongyun
 * @date 2020/5/29 11:40
 */
@Slf4j
public class TokenFilter implements Filter {

    private String excludedPaths;
    private String logoutPath;
    private String loginPath;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Value("${sso.jwt.expiration:3600}")
    private Long expiration;

    @Value("${sso.redirect:}")
    private boolean redirect = true;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private TokenHelper tokenHelper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init token filter config");
        logoutPath = filterConfig.getInitParameter(SsoConstant.LOGOUT_PATH);
        excludedPaths = filterConfig.getInitParameter(SsoConstant.EXCLUDED_PATHS);
        loginPath = filterConfig.getInitParameter(SsoConstant.LOGIN_PATH);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException, BusinessException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 请求方法是OPTION 则跳过
        if ("OPTIONS".equals(request.getMethod())) {
            log.debug("url {} method is option, need not authenticate, ignored", request.getRequestURI());
            filterChain.doFilter(req, resp);
            return;
        }
        String servletPath = request.getServletPath();
        // 忽略的路径 跳过
        if (StrUtil.isNotBlank(excludedPaths)) {
            for (String excludedPath : excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();

                if (antPathMatcher.match(uriPattern, servletPath)) {
                    filterChain.doFilter(req, resp);
                    return;
                }

            }
        }


        //校验jwt
        try {
            String authHeader = request.getHeader(SsoConstant.TOKEN_HEADER);
            if (StrUtil.isNotBlank(authHeader) && authHeader.startsWith(SsoConstant.TOKEN_HEAD)) {
                String authToken = authHeader.substring(SsoConstant.TOKEN_HEAD.length());
                if (StrUtil.isNotBlank(authToken) && !Jwts.parser().isSigned(authToken)) {
                    Claims claims = tokenHelper.getClaimsFromToken(authToken);
                    String issuer = claims.getIssuer();
                    String redisKey = SsoConstant.TOKEN_USER_DATA + issuer;

                    //logout忽略 并清除数据
                    if (logoutPath != null && logoutPath.trim().length() > 0 && logoutPath.equals(servletPath)) {
                        redisManager.remove(redisKey);
                        filterChain.doFilter(req, resp);
                        return;
                    }

                    //redis存在并且合法 刷新时间
                    if (redisManager.exists(redisKey)) {
                        TokenUserData tokenUserData = JSONUtil.toBean(redisManager.get(redisKey), TokenUserData.class);
                        String jwtId = (String) claims.get(SsoConstant.CLAIM_KEY_JWT_ID);
                        //防止过时的jwt能够使用
                        if (!tokenUserData.getJwtId().equals(jwtId)) {
                            log.info("redis中不存在{}", redisKey);
                            throw new BusinessException(SsoErrorCode.UNAUTHORIZED);
                        }
                        //延长过期时间
                        redisManager.expire(redisKey, expiration, TimeUnit.SECONDS);
                        //request设置姓名
                        request.setAttribute(SsoConstant.USERNAME, issuer);
                        filterChain.doFilter(req, resp);
                        return;
                    } else {
                        log.info("用户{}JwtId不存在", issuer);
                        throw new BusinessException(SsoErrorCode.UNAUTHORIZED);
                    }

                } else {
                    log.info("缺少JWT，或者缺少签名");
                    throw new BusinessException(SsoErrorCode.UNAUTHORIZED);
                }
            } else {
                log.info("缺少Authorization Bearer");
                throw new BusinessException(SsoErrorCode.UNAUTHORIZED);
            }
        } catch (BusinessException e) {
            //未授权  返回401 或者跳回登录
            if (redirect){
                response.sendRedirect(loginPath);
            } else  {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().flush();
            }
        }
    }

}
