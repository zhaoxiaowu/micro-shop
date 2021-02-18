package com.zxw.common.web.exception;

/**
 * @author wuhongyun
 * @date 2020/5/25 17:40
 */
public class ImpossibleException extends RuntimeException {

    public ImpossibleException() {
        super("这种情况不可能发生!");
    }

    public ImpossibleException(Throwable cause) {
        super(cause);
    }

    protected ImpossibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ImpossibleException(String message) {
        super(message);
    }

    public ImpossibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
