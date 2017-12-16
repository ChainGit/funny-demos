package com.chain.project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//这个不是用于配置的，只是一个工具类
@Component
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    //是否启用加密
    @Value("${app.crypto.status}")
    private Boolean encrypt;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private Environment env;

    @Autowired
    private MoreConfig moreConfig;

    public AppConfig() {
        logger.info("AppConfig constructor");
    }

    public String getProperty(String key) {
        return env.getProperty(key);
    }

    public Object getBean(String name) {
        return ctx.getBean(name);
    }

    public <T> T getBean(String name, Class<T> clazz) {
        return ctx.getBean(name, clazz);
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public MoreConfig getMoreConfig() {
        return moreConfig;
    }

    public void setMoreConfig(MoreConfig moreConfig) {
        this.moreConfig = moreConfig;
    }
}
