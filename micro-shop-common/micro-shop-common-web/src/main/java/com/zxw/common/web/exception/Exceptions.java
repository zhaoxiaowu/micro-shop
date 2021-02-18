package com.zxw.common.web.exception;

/**
 * @author wuhongyun
 * @date 2020/5/26 18:24
 */
public class Exceptions {

    public static ImpossibleException throwImpossibleException() {
        return new ImpossibleException();
    }

    public static BusinessException throwBusinessException(String message) {
        return new BusinessException(message);
    }

    public static BusinessException throwBusinessException(String code, String message) {
        return new BusinessException(code, message);
    }

    public static BusinessException throwBusinessException(BusinessErrorStatus businessErrorStatus) {
        return new BusinessException(businessErrorStatus);
    }


    public static IllegalStateException throwIllegalStateException(String message, Throwable e) {
        return new IllegalStateException(message, e);
    }

    public static IllegalStateException throwIllegalStateException(String message) {
        return new IllegalStateException(message);
    }
}
