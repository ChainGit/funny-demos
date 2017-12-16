package com.chain.project.common.intercepter;

import com.chain.project.config.AppConfig;
import com.chain.utils.crypto.*;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.utils.ChainProjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonStringInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(JsonStringInterceptor.class);

    @Autowired
    private CryptoFactoryBean cryptoFactoryBean;

    @Autowired
    private AppConfig appConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        logger.info("perHandle");

        /* 约定：
         * 1、加密的数据参数key为"s"，不加密的数据参数key为"ns".
         * 2、先处理s，再处理ns，若s不为空，则ns不再处理.
         */
        String jsonEncryptStr = httpServletRequest.getParameter(Constant.REQUEST_ENCRYPT_JSON_KEY);
        String jsonPlainStr = httpServletRequest.getParameter(Constant.REQUEST_PLAIN_JSON_KEY);
        if (!ChainProjectUtils.isEmpty(jsonEncryptStr)) {
            if (appConfig.isEncrypt()) {
                String jsonDecryptStr = null;
                try {
                    jsonDecryptStr = decrypt(jsonEncryptStr, true);
                } catch (Exception e) {
                    logger.error("not a correct encrypted json string");
                }
                if (jsonDecryptStr == null)
                    return false;
                httpServletRequest.setAttribute(Constant.JSON_MAP, jsonDecryptStr);
            } else {
                httpServletRequest.setAttribute(Constant.JSON_MAP, jsonEncryptStr);
            }
        } else if (!ChainProjectUtils.isEmpty(jsonPlainStr)) {
            httpServletRequest.setAttribute(Constant.JSON_MAP, jsonPlainStr);
        } else {
            //两者均为空，创建一个空的JSON_MAP
            httpServletRequest.setAttribute(Constant.JSON_MAP, "{}");
        }

        //非生成环境打印request请求中的"s"和"ns"数据，也就是Attribute中的JSON_MAP
        if (!Constant.PROD_MODE.equals(appConfig.getProperty("spring.profiles.active"))) {
            if (!ChainProjectUtils.isEmpty(jsonEncryptStr) &&
                    Constant.TEST_MODE.equals(appConfig.getProperty("spring.profiles.active"))) {
                logger.info("Request before decrypt string: \n" + jsonEncryptStr);
            }
            logger.info("Request JsonMap Parameter: " + httpServletRequest.getAttribute(Constant.JSON_MAP));
        }
        return true;
    }

    //根据配置文件信息进行解密
    private String decrypt(String s, boolean singleTon) {
        String recvMode = appConfig.getProperty("app.crypto.recv-default-mode");
        if (ChainProjectUtils.isEmpty(recvMode))
            return s;
        switch (recvMode) {
            case "aes": {
                AESUtils aesUtils = cryptoFactoryBean.getAesUtils(singleTon);
                s = aesUtils.decrypt(s);
                break;
            }
            case "des": {
                DESUtils desUtils = cryptoFactoryBean.getDesUtils(singleTon);
                s = desUtils.decrypt(s);
                break;
            }
            case "base64": {
                s = Base64Decoder.decode(s);
                break;
            }
            case "rsa-pri": {
                RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(singleTon);
                s = rsaUtils.decryptByPrivateKey(s);
                break;
            }
            case "rsa-pub": {
                RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(singleTon);
                s = rsaUtils.decryptByPublicKey(s);
                break;
            }
            case "md5": {
                s = MD5Utils.encrypt(s);
                break;
            }
            //case "none":
            //default:
        }
        return s;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
