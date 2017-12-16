package com.chain.project.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainProjectException extends Exception {

    private static Logger logger = LoggerFactory.getLogger(ChainProjectException.class);

    private int errorCode = ErrorCode.DEFAULT;

    public ChainProjectException() {
        super();
        logger.error("car server exception");
    }

    public ChainProjectException(String message) {
        super(message);
        logger.error("car server exception: " + message);
    }

    public ChainProjectException(String message, Throwable cause) {
        super(message, cause);
        logger.error("car server exception: " + message, cause);
    }

    public ChainProjectException(Throwable cause) {
        super(cause);
        logger.error("car server exception", cause);
    }

    protected ChainProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("car server exception: " + message, cause);
    }

    public ChainProjectException(int code, String message) {
        this(message);
        errorCode = code;
    }

    public ChainProjectException(int code, String message, Throwable cause) {
        this(message, cause);
        errorCode = code;
    }

    public ChainProjectException(int code) {
        this();
        errorCode = code;
    }

    public ChainProjectException(int code, Throwable cause) {
        this(cause);
        errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ChainProjectException setErrorCode(int code) {
        errorCode = code;
        return this;
    }
}
