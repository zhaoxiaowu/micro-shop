package com.zxw.common.web.exception;

import com.zxw.common.web.web.ApiErrorCode;

/**
 * 业务异常
 *
 * @author wuhongyun
 * @date 2020/5/25 17:51
 */
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String message) {
        super(message);
        this.code = ApiErrorCode.input_params_error.value;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(BusinessErrorStatus businessErrorStatus) {
        this(businessErrorStatus.getCode(), businessErrorStatus.getDesc());
    }


    public String getCode() {
        return code;
    }
}
