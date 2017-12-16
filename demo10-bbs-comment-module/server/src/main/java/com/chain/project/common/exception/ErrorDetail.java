package com.chain.project.common.exception;

/**
 * 错误详情
 */
public class ErrorDetail {

    private int code;
    private String msg;

    {
        code = ErrorCode.DEFAULT;
        msg = ErrorCode.getErrorMsg(code);
    }

    private ErrorDetail() {

    }

    public int getCode() {
        return code;
    }

    public static ErrorDetail setCodeOnly(int code) {
        ErrorDetail error = new ErrorDetail();
        error.code = code;
        error.msg = ErrorCode.getErrorMsg(code);
        return error;
    }

    public String getMsg() {
        return msg;
    }

    public static ErrorDetail setMsgOnly(String msg) {
        ErrorDetail error = new ErrorDetail();
        error.code = ErrorCode.DEFAULT;
        error.msg = msg;
        return error;
    }

    public static ErrorDetail of(int code, String msg) {
        ErrorDetail error = new ErrorDetail();
        error.code = code;
        error.msg = msg;
        return error;
    }

    public static ErrorDetail ofDefault() {
        ErrorDetail error = new ErrorDetail();
        return error;
    }

    @Override
    public String toString() {
        return code + " [" + msg + "]";
    }
}
