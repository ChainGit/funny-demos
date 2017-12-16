package com.chain.project.unit.utils;

import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.unit.entities.UserEntity;
import com.chain.utils.crypto.MD5Utils;

public class BbsUtils {

    public static String encryptPassword(String password, Long userId) {
        String epassword = null;
        epassword = MD5Utils.encrypt(password + userId);
        return epassword;
    }

    public static boolean verifyPassword(UserEntity userEntity, String passwordToVerify) {
        if (ChainProjectUtils.isEmpty(passwordToVerify) || ChainProjectUtils.isEmpty(userEntity))
            return false;
        Long userId = userEntity.getUserId();
        String passwordInDb = userEntity.getPassword();
        passwordToVerify = encryptPassword(passwordToVerify, userId);
        if (passwordToVerify.equals(passwordInDb))
            return true;
        return false;
    }
}
