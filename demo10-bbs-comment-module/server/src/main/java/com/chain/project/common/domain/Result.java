package com.chain.project.common.domain;


import com.chain.project.common.exception.ErrorDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 返回的结果
 * <p>
 * 链式编程法，方便级联赋值
 */
public class Result {

    // private static final String DEFAULT_LANGUAGE="EN";
    private static final String DEFAULT_LANGUAGE = "CN";
    private static final String SUCCESS_EN = "success";
    private static final String FAILURE_EN = "failure";
    private static final String ERROR_EN = "error";
    private static final String EMPTY_DATA_EN = "empty data";
    private static final String SUCCESS_CN = "成功";
    private static final String FAILURE_CN = "失败";
    private static final String ERROR_CN = "错误";
    private static final String EMPTY_DATA_CN = "数据为空";
    public static String SUCCESS;
    public static String FAILURE;
    public static String ERROR;
    public static String EMPTY_DATA;

    static {
        switch (DEFAULT_LANGUAGE) {
            case "CN":
                SUCCESS = SUCCESS_CN;
                FAILURE = FAILURE_CN;
                ERROR = ERROR_CN;
                EMPTY_DATA = EMPTY_DATA_CN;
                break;
            default:
                SUCCESS = SUCCESS_EN;
                FAILURE = FAILURE_EN;
                ERROR = ERROR_EN;
                EMPTY_DATA = EMPTY_DATA_EN;
                break;
        }
    }

    private Logger logger = LoggerFactory.getLogger(Result.class);
    private boolean success = false;
    private String msg = "";
    private Object data = null;

    /**
     * 对于spring的json转换结果是否需要加密的标识，默认为true即加密
     */
    private Boolean encrypt = true;

    private String[] ignore;

    public Result() {
    }

    public Result(boolean success, String msg, String[] ignore) {
        super();
        this.success = success;
        this.msg = msg;
        this.ignore = ignore;
    }

    public static Result fail() {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(FAILURE);
        return result;
    }

    public static Result fail(String[] ignore) {
        Result result = fail();
        result.setIgnore(ignore);
        return result;
    }

    public static Result fail(String msg) {
        Result result = fail();
        result.setMsg(msg);
        return result;
    }

    public static Result fail(String msg, String[] ignore) {
        Result result = fail();
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    public static Result fail(Object data, String msg) {
        Result result = fail();
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(Object data, String msg, String[] ignore) {
        Result result = fail();
        result.setData(data);
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    public static Result fail(ErrorDetail error) {
        Result result = fail();
        result.setData(error);
        return result;
    }

    // 容易与fail(String msg)混淆
    //    public static Result fail(Object data) {
    //        Result result = fail();
    //        result.setData(data);
    //        return result;
    //    }

    public static Result ok() {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(SUCCESS);
        return result;
    }

    public static Result ok(String[] ignore) {
        Result result = ok();
        result.setIgnore(ignore);
        return result;
    }

    public static Result ok(String msg) {
        Result result = ok();
        result.setMsg(msg);
        return result;
    }

    public static Result ok(String msg, String[] ignore) {
        Result result = ok();
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    public static Result ok(Object data, String msg) {
        Result result = ok();
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result ok(Object data, String msg, String[] ignore) {
        Result result = ok();
        result.setData(data);
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    // 容易与ok(String msg)混淆
    //    public static Result ok(Object data) {
    //        Result result = ok();
    //        result.setData(data);
    //        return result;
    //    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String[] getIgnore() {
        return ignore;
    }

    public Result setIgnore(String[] ignore) {
        this.ignore = ignore;
        return this;
    }

    public Boolean isEncrypt() {
        return encrypt;
    }

    public Result setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
        return this;
    }


}

