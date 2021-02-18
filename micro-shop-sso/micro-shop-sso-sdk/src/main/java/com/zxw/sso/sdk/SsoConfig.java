package com.zxw.sso.sdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuhongyun
 * @date 2020/5/29 12:26
 */
@Configuration
public class SsoConfig {

    @Value("${sso.login.path:http://localhost:9999}")
    private String ssoLoginPath;

    @Value("${sso.logout.path:logout}")
    private String ssoLogoutPath;

    @Value("${sso.excluded.paths:}")
    private String ssoExcludedPaths;



    @Bean
    public FilterRegistrationBean xxlSsoFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("TokenFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new TokenFilter());
        registration.addInitParameter(SsoConstant.LOGOUT_PATH, ssoLogoutPath);
        registration.addInitParameter(SsoConstant.LOGIN_PATH, ssoLoginPath);
        registration.addInitParameter(SsoConstant.EXCLUDED_PATHS, ssoExcludedPaths);

        return registration;
    }


}
