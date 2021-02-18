package com.zxw.sso.sdk;

import com.zxw.common.web.exception.BusinessErrorStatus;

/**
 * @author wuhongyun
 * @date 2020/5/26 18:15
 */
public enum SsoErrorCode implements BusinessErrorStatus {

    LOGIN_FAILED("A0101", "用户名或密码错误"),
    UNAUTHORIZED("A0102", "暂未登录或登录已过期"),
    THIRD_AUTH_FAILED("A0103", "第三方授权失败")
    ;


    private String code;

    private String message;

    SsoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.message;
    }
}
