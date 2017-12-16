package com.chain.project.common.exception;

public class ErrorMsg {

    //默认错误
    public static final String DEFAULT = "default error";

    //测试事务时随机产生的错误
    public static final String RANDOM_DISASTER = "random disaster";

    //客户端错误
    public static final String CLIENT = "client error";

    //服务器内部错误
    public static final String SERVER = "server error";

    //客户端传入参数错误
    public static final String PARAM_IN = "param in error";

    //业务错误
    public static final String BUSINESS = "business error";

    //事务回滚
    public static final String ROLL_BACK = "roll back operation";

}
