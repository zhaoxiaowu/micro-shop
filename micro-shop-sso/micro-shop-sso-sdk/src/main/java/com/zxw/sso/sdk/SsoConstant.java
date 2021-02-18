package com.zxw.sso.sdk;

/**
 * @author wuhongyun
 * @date 2020/5/29 12:26
 */
public class SsoConstant {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_HEAD = "Bearer";
    public static final String CLAIM_KEY_JWT_ID = "jid";
    public static final String USERNAME = "username";
    public static final String SOURCE_WEB = "WEB";

    /** 忽略的URL配置 */
    public static final String EXCLUDED_PATHS = "exclude_paths";
    /** 登出地址 */
    public static final String LOGOUT_PATH = "logout_path";

    /** 登录地址 */
    public static final String LOGIN_PATH = "login_path";

    /** redis key */
    public static final String TOKEN_USER_DATA ="TOKEN_USER_DATA:";

}
