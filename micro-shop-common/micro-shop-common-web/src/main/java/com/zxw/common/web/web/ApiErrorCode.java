package com.zxw.common.web.web;

/**
 * @author wuhongyun
 * @date 2020/5/25 18:13
 */
public enum ApiErrorCode {
    /**
     * 正常返回数据
     */
    ok("0"),

    /**
     * 服务器错误
     */
    internal_server_error("1"),

    /**
     * 请求参数效验错误
     */
    input_params_error("2"),

    /**
     * 非法状态
     */
    illegal_state("3"),


    /**
     * http方法不支持
     */
    http_request_method_not_supported("4"),

    /**
     * 超过最大上传大小
     */
    maximum_upload_size_exceeded("5");


    public String value;

    ApiErrorCode(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name();
    }
}

