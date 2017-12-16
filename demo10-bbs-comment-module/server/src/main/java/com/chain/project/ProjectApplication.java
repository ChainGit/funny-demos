package com.chain.project;

import com.chain.project.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//其实springMVC4也是能实现零配置文件的，spring-boot只是做了更进一步的自动配置
//SpringBootApplication已经包含了ComponentScan和EnableAutoConfiguration
//默认会自动执行一些AutoConfiguration，如果不希望某个自动配置，使用excludes就行
//明确指定那些需要自动配置，则不使用@SpringBootApplication，而使用@Import逐个指定就行
@SpringBootApplication
//自动配置的具体流程参考源码中的public ConfigurableApplicationContext run(String... args)
@RestController
@ServletComponentScan
public class ProjectApplication {

    private Logger logger = LoggerFactory.getLogger(ProjectApplication.class);

    @Autowired
    private AppConfig config;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    private static String info;

    @RequestMapping("/")
    public String index() {
        if (info == null) {
            logger.info("application start success!");
            info = config.getProperty("app.name") + " " + config.getProperty("app.version")
                    + " [" + config.getProperty("spring.profiles.active") + "]";
        }
        return info;
    }

}


