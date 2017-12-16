package com.chain.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//更多配置信息
@Component
@Configuration
@PropertySource("classpath:more.properties")
@AutoConfigureBefore(AppConfig.class)
public class MoreConfig {

    @Value("${pingxx.apikey}")
    private String pingxxKey;

    public String getPingxxKey() {
        return pingxxKey;
    }

    public void setPingxxKey(String pingxxKey) {
        this.pingxxKey = pingxxKey;
    }

}
