package com.chain.project.common.exception;

/**
 * 手动触发回滚事务作用：在update,delete,insert操作时发生错误或者不符合业务预期结果的一定不要忘记回滚!!!
 */
public class DoRollBack extends RuntimeException {


    public DoRollBack() {
    }

    public DoRollBack(String message) {
        super(message);
    }

    public DoRollBack(String message, Throwable cause) {
        super(message, cause);
    }

    public DoRollBack(ErrorDetail error) {
        this(error.toString());
    }

    public DoRollBack(ErrorDetail error, Throwable cause) {
        this(error.toString(), cause);
    }

    public DoRollBack(Throwable cause) {
        super(cause);
    }

    public DoRollBack(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
