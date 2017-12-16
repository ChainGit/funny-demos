package com.chain.project.unit.domain;

import com.chain.project.common.exception.ErrorCode;
import com.chain.project.common.exception.ErrorMsg;

import java.util.HashMap;
import java.util.Map;

public class BbsErrorCode extends ErrorCode {

    private static Map<Integer, String> errors = null;

    //评论为空
    public static final int TEXT_EMPTY = 101;
    //评论太短
    public static final int TEXT_SHORT = 102;
    //包含非法或敏感词汇
    public static final int TEXT_ILLEGAL = 103;
    //发言太快
    public static final int TEXT_FAST = 104;

    static {
        errors = new HashMap<>();
        errors.put(null, ErrorMsg.DEFAULT);
        errors.put(TEXT_EMPTY, BbsErrorMsg.TEXT_EMPTY);
        errors.put(TEXT_SHORT, BbsErrorMsg.TEXT_SHORT);
        errors.put(TEXT_ILLEGAL, BbsErrorMsg.TEXT_ILLEGAL);
        errors.put(TEXT_FAST, BbsErrorMsg.TEXT_FAST);
    }

    public static String getErrorMsg(int errorCode) {
        return errors.get(errorCode);
    }

}
