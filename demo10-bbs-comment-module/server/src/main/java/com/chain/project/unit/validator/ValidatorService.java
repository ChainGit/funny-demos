package com.chain.project.unit.validator;

public interface ValidatorService {

    /**
     * 用户是否存在
     *
     * @param userId
     * @return
     */
    boolean isUserExist(Long userId);

    /**
     * 检查发言的内容
     *
     * @param text
     * @return
     */
    int checkText(Long userId, String text);

    /**
     * 是否发言太快
     *
     * @param userId
     * @return
     */
    boolean isTooFast(Long userId);

}

