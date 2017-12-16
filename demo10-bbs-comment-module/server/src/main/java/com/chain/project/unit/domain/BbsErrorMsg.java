package com.chain.project.unit.domain;

import com.chain.project.common.exception.ErrorMsg;

public class BbsErrorMsg extends ErrorMsg {

    //评论为空
    public static final String TEXT_EMPTY = "empty text";
    //评论太短
    public static final String TEXT_SHORT = "text is too short, at least " + BbsConstant.TEXT_MIN;
    //包含非法或敏感词汇
    public static final String TEXT_ILLEGAL = "illegal content or word";
    //发言太快
    public static final String TEXT_FAST = "speak too fast, at least " + BbsConstant.TEXT_LIMIT + " min once";
}
