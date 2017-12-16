package com.chain.project.common.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {

    private static Map<Integer, String> errors = null;

    //默认错误
    public static final int DEFAULT = 10000;

    //测试事务时随机产生的错误
    public static final int RANDOM_DISASTER = 10010;

    //客户端错误
    public static final int CLIENT = 10011;

    //服务器内部错误
    public static final int SERVER = 10012;

    //客户端传入参数错误
    public static final int PARAM_IN = 10013;

    //业务错误
    public static final int BUSINESS = 10014;

    //事务回滚
    public static final int ROLL_BACK = 10015;

    static {
        errors = new HashMap<>();
        errors.put(null, ErrorMsg.DEFAULT);
        errors.put(DEFAULT, ErrorMsg.DEFAULT);
        errors.put(RANDOM_DISASTER, ErrorMsg.RANDOM_DISASTER);
        errors.put(CLIENT, ErrorMsg.CLIENT);
        errors.put(SERVER, ErrorMsg.SERVER);
        errors.put(PARAM_IN, ErrorMsg.PARAM_IN);
        errors.put(BUSINESS, ErrorMsg.BUSINESS);
        errors.put(ROLL_BACK, ErrorMsg.ROLL_BACK);
    }

    public static String getErrorMsg(int errorCode) {
        return errors.get(errorCode);
    }

}
