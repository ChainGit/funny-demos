package com.chain.project.test;

import com.chain.utils.crypto.MD5Utils;
import org.junit.Test;

public class CryptoTest {

    //生成AES密钥和偏移向量
    @Test
    public void testAes() {
        String key = MD5Utils.encrypt("ABC123");
        //CryptoJs必须32位
        System.out.println(key.substring(0, 16));
        //iv必须为16位
        String iv = MD5Utils.encrypt(key);
        System.out.println(iv.substring(0, 16));
    }

    //计算MD5值
    @Test
    public void testMd5() {
        String id = "3";
        String password = "123456";
        //        String s = RandomStringUtils.generateString(6) + new Date().getTime();
        String md5 = MD5Utils.encrypt(MD5Utils.encrypt(password) + id);
//        String md5 = MD5Utils.encrypt("e10adc3949ba59abbe56e057f20f883e"+ id);
        System.out.println(md5);
    }
}
