package com.chain.project.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

//相当于原来的web.xml，Servlet3.0
//如果是war包运行方式，则需要另外创建类，实现implements WebApplicationInitializer，且加上@Order(1)
//如果是jar包运行方式，则使用这个类
//默认自动配置，不需要手动配置，不过也可以使用@Bean的方式来配置，来继承(覆盖)自动配置
@Configuration
public class WebConfig {

    private Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private AppConfig appConfig;

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        logger.info("create servletRegistrationBean");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        //示例
        //servletRegistrationBean.addUrlMappings("/demo-servlet2");
        //servletRegistrationBean.setServlet(new DemoServlet2());
        Map<String, String> map = new HashMap<>();
        List<String> urls = new ArrayList<>();
        map.put("loginUsername", appConfig.getProperty("spring.datasource.druid.stat-view-servlet.login-username"));
        map.put("loginPassword", appConfig.getProperty("spring.datasource.druid.stat-view-servlet.login-password"));
        map.put("resetEnable", appConfig.getProperty("spring.datasource.druid.stat-view-servlet.reset-enable"));
        servletRegistrationBean.setInitParameters(map);
        StatViewServlet statViewServlet = new StatViewServlet();
        servletRegistrationBean.setServlet(statViewServlet);
        urls.add("/druid/*");
        servletRegistrationBean.setUrlMappings(urls);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        logger.info("create filterRegistrationBean");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Set<String> set = new HashSet<>();
        List<String> urls = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        //示例
        //filterRegistrationBean.setFilter(new OpenSessionInViewFilter());
        //set.add("/");
        map.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(map);
        WebStatFilter webStatFilter = new WebStatFilter();
        webStatFilter.setSessionStatEnable(true);
        webStatFilter.setProfileEnable(true);
        filterRegistrationBean.setFilter(webStatFilter);
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        filterRegistrationBean.setUrlPatterns(set);
        return filterRegistrationBean;
    }

    //@Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        logger.info("create servletListenerRegistrationBean");
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        //示例
        //servletListenerRegistrationBean.setListener(new Log4jConfigListener());
        //servletListenerRegistrationBean.addInitParameter("log4jConfigLocation", "classpath:log4j.properties");
        return servletListenerRegistrationBean;
    }
}
