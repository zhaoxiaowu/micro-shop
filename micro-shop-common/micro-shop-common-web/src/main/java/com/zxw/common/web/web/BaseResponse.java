package com.zxw.common.web.web;

import com.zxw.common.web.exception.BusinessErrorStatus;

import java.io.Serializable;

/**
 * @author wuhongyun
 * @date 2020/5/25 17:38
 */
public abstract class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    private T returnObject;

    /**
     * 提示信息
     */
    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }


    public static JSONResponse failed() {
        JSONResponse ret = new JSONResponse();
        ret.setErrorCode(ApiErrorCode.internal_server_error.value);
        ret.setMessage(ApiErrorCode.internal_server_error.getName());
        return ret;
    }

    public static JSONResponse failed(String message) {
        JSONResponse ret = new JSONResponse();
        ret.setErrorCode(ApiErrorCode.internal_server_error.value);
        ret.setMessage(message);
        return ret;
    }

    public static JSONResponse failed(BusinessErrorStatus returnStatus) {
        JSONResponse ret = new JSONResponse();
        ret.setErrorCode(returnStatus.getCode());
        ret.setMessage(returnStatus.getDesc());
        return ret;
    }

    public static JSONResponse succeed(Object obj) {
        JSONResponse ret = new JSONResponse();
        ret.setErrorCode(ApiErrorCode.ok.value);
        ret.setReturnObject(obj);
        ret.setMessage("success");
        return ret;
    }
}
