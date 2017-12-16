package com.chain.project.common.converter;

import com.chain.project.config.AppConfig;
import com.chain.utils.ReflectionUtils;
import com.chain.utils.crypto.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chain.project.base.entities.BaseEntity;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.domain.Result;
import com.chain.project.common.utils.ChainProjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

@Component
public class ObjectToJsonStringConverter extends MappingJackson2HttpMessageConverter {

    private static Logger logger = LoggerFactory.getLogger(ObjectToJsonStringConverter.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CryptoFactoryBean cryptoFactoryBean;

    // 重写 writeInternal 方法，在返回内容前首先进行加密，注意是有Type的父类方法
    @Override
    public void writeInternal(Object object, Type type,
                              HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
//        logger.info("MyMappingJackson2HttpMessageConverter writeInternal");
//        super.writeInternal(object, type, outputMessage);

        MediaType contentType = outputMessage.getHeaders().getContentType();
        MediaType[] supportedMediaTypes = {MediaType.APPLICATION_JSON, new MediaType("application", "*+json")};
        //只有是application/json时才进行转换
        if (!(contentType != null && (contentType.includes(supportedMediaTypes[0])) || contentType.includes(supportedMediaTypes[1])))
            return;

        ObjectMapper mapper = new ObjectMapper();
        if (object == null) {
            //创建一个空的Result对象
            Result result = new Result();
            if (appConfig.isEncrypt())
                result.setEncrypt(true);
            else
                result.setEncrypt(false);
            object = result;
        }
        boolean resultEncrypt = true;
        if (object instanceof Result) {
            Result result = (Result) object;
            resultEncrypt = result.isEncrypt();
            String[] ignores = result.getIgnore();
            if (ChainProjectUtils.isEmpty(ignores))
                result.setIgnore(ChainProjectUtils.getDefaultIgnoreArray());
            else
                result.setIgnore(ChainProjectUtils.concatStringArray(ignores, ChainProjectUtils.getDefaultIgnoreArray()));

            //FIXME: 这个方法还不完善，暂时不用
            result.setIgnore(null);//暂时取消所有的忽略

            ignoreResultValue(result);
            //设置的mapper忽略会忽略Object内的所有value为null的值
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        //对于时间，Jackson默认是转成timestamp形式的，即一个long型数
        String json0 = mapper.writeValueAsString(object);
        String json = json0;
        if (appConfig.isEncrypt() && resultEncrypt) {
            json = encrypt(json0, true);
        }
        OutputStream body = outputMessage.getBody();
        body.write(json.getBytes());
        body.flush();

        //非生成环境下打印返回的原始非加密数据（不是打印一切返回的数据的意思）
        if (!Constant.PROD_MODE.equals(appConfig.getProperty("spring.profiles.active"))) {
            logger.info("Response Data: " + json0);
            if (appConfig.isEncrypt() &&
                    Constant.TEST_MODE.equals(appConfig.getProperty("spring.profiles.active"))) {
                logger.info("Response after encrypt data: \n" + json);
            }
        }
    }

    //根据配置文件的加密选项进行加密
    private String encrypt(String s, boolean singleTon) {
        String sendMode = appConfig.getProperty("app.crypto.send-default-mode");
        if (ChainProjectUtils.isEmpty(sendMode))
            return s;
        switch (sendMode) {
            case "aes": {
                AESUtils aesUtils = cryptoFactoryBean.getAesUtils(singleTon);
                s = aesUtils.encrypt(s);
                break;
            }
            case "des": {
                DESUtils desUtils = cryptoFactoryBean.getDesUtils(singleTon);
                s = desUtils.encrypt(s);
                break;
            }
            case "base64": {
                s = Base64Encoder.encode(s);
                break;
            }
            case "rsa-pri": {
                RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(singleTon);
                s = rsaUtils.encryptByPrivateKey(s);
                break;
            }
            case "rsa-pub": {
                RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(singleTon);
                s = rsaUtils.encryptByPublicKey(s);
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

    /**
     * 根据Result的ignore忽略值，只适合Result且Result内的data为BaseEntity的子类，
     * 暂未支持如集合、集合的嵌套、Map等
     *
     * @param result
     */
    private void ignoreResultValue(Result result) {
        if (ChainProjectUtils.isEmpty(result))
            return;
        String[] ignores = result.getIgnore();
        if (ignores != null && ignores.length > 0) {
            Object data = result.getData();
            if (data != null) {
                if (data instanceof BaseEntity) {
                    try {
                        for (String s : ignores) {
                            Field f = ReflectionUtils.getDeclaredField(data, s);
                            if (f != null) {
                                try {
                                    //设置成null即可
                                    ReflectionUtils.setField(data, f, null);
                                } catch (Exception e) {
                                    //发生错误继续
                                    continue;
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("ignoreResultValue" + e);
                    }
                }
            }
        }
        result.setIgnore(null);
        result.setEncrypt(null);
    }

}
