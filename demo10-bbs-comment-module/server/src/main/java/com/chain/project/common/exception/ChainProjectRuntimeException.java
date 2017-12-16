package com.chain.project.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainProjectRuntimeException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(ChainProjectRuntimeException.class);

    private int errorCode = ErrorCode.DEFAULT;

    public ChainProjectRuntimeException() {
        super();
        logger.error("car server runtime exception");
    }

    public ChainProjectRuntimeException(String message) {
        super(message);
        logger.error("car server runtime exception: " + message);
    }

    public ChainProjectRuntimeException(String message, Throwable cause) {
        super(message, cause);
        logger.error("car server runtime exception: " + message, cause);
    }

    public ChainProjectRuntimeException(Throwable cause) {
        super(cause);
        logger.error("car server runtime exception", cause);
    }

    protected ChainProjectRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("car server runtime exception: " + message, cause);
    }

    public ChainProjectRuntimeException(int code, String message) {
        this(message);
        errorCode = code;
    }

    public ChainProjectRuntimeException(int code, String message, Throwable cause) {
        this(message, cause);
        errorCode = code;
    }

    public ChainProjectRuntimeException(int code) {
        this();
        errorCode = code;
    }

    public ChainProjectRuntimeException(int code, Throwable cause) {
        this(cause);
        errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ChainProjectRuntimeException setErrorCode(int code) {
        errorCode = code;
        return this;
    }
}
