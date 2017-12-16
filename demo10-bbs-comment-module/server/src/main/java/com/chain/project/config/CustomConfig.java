package com.chain.project.config;

import com.chain.project.common.converter.DatePropertyEditorSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

//一些自定义的配置可以放在这，比如Kaptcha验证码生成器等
@Configuration
public class CustomConfig {

    private static Logger logger = LoggerFactory.getLogger(CustomConfig.class);

    @Bean
    public static CustomEditorConfigurer customEditorConfigurer() {
        logger.info("create customEditorConfigurer");
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        Map<Class<?>, Class<? extends PropertyEditor>> customEditors = new HashMap<>();
        customEditors.put(java.util.Date.class, DatePropertyEditorSupport.class);
        customEditorConfigurer.setCustomEditors(customEditors);
        return customEditorConfigurer;
    }

}
