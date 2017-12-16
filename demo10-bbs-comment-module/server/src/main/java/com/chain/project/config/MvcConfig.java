package com.chain.project.config;

import com.chain.utils.FileDirectoryUtils;
import com.chain.utils.RandomStringUtils;
import com.chain.utils.crypto.CryptoFactoryBean;
import com.chain.project.common.converter.ObjectToJsonStringConverter;
import com.chain.project.common.converter.StringToJsonMapConverter;
import com.chain.project.common.exception.ChainProjectRuntimeException;
import com.chain.project.common.formatter.CarServerDateFormatter;
import com.chain.project.common.intercepter.JsonStringInterceptor;
import com.chain.project.common.intercepter.MeasurementInterceptor;
import com.chain.project.common.intercepter.MonitorRequestInterceptor;
import com.chain.project.common.utils.ChainProjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Configuration
//一个web项目，最重要的就是Mvc相关的控制
//这里启用WebMvcConfigurer的自定义配置，即不使用WebMvcAutoConfiguration
@EnableWebMvc
@AutoConfigureAfter({WebConfig.class})
public class MvcConfig implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    @Bean
    public AppConfig appConfig() {
        return new AppConfig();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
//        defaultServletHandlerConfigurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(stringToJsonMapConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("add interceptors");
        //注意是有先后顺序的
        //MonitorRequestInterceptor自定义拦截器
        registry.addInterceptor(monitorRequestInterceptor()).addPathPatterns("/**");
        //MeasurementInterceptor自定义拦截器
        registry.addInterceptor(measurementInterceptor()).addPathPatterns("/**");
        //JsonStringInterceptor自定义拦截器
        registry.addInterceptor(jsonStringInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public MonitorRequestInterceptor monitorRequestInterceptor() {
        return new MonitorRequestInterceptor();
    }

    @Bean
    public MeasurementInterceptor measurementInterceptor() {
        return new MeasurementInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    //拦截器的加载再contextLoader之前，所以需要先获取bean
    @Bean
    public JsonStringInterceptor jsonStringInterceptor() {
        return new JsonStringInterceptor();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("extendMessageConverters");
        for (int i = 0; i < converters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = converters.get(i);
            if (httpMessageConverter.getClass().equals(MappingJackson2HttpMessageConverter.class)) {
                converters.set(i, objectToJsonStringConverter());
            }
        }
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }


    @Bean
    public ObjectToJsonStringConverter objectToJsonStringConverter() {
        return new ObjectToJsonStringConverter();
    }

    @Bean(name = "conversionService")
    public ConversionService conversionService() {
        logger.info("create bean conversionService");
        FormattingConversionServiceFactoryBean conversionServiceFactoryBean = new FormattingConversionServiceFactoryBean();
        Set<Converter> converters = new LinkedHashSet<>();
        Set<Formatter> formatters = new LinkedHashSet<>();
        converters.add(stringToJsonMapConverter());
        conversionServiceFactoryBean.setConverters(converters);
        formatters.add(carServerDateFormatter());
        conversionServiceFactoryBean.setFormatters(formatters);
        return conversionServiceFactoryBean.getObject();
    }


    @Bean
    public CarServerDateFormatter carServerDateFormatter() {
        return new CarServerDateFormatter();
    }

    @Bean
    public StringToJsonMapConverter stringToJsonMapConverter() {
        return new StringToJsonMapConverter();
    }

    @Bean
    public CryptoFactoryBean cryptoFactoryBean() {
        logger.info("create cryptoFactoryBean");
        AppConfig appConfig = appConfig();
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        String rsaPublicKeyFile = appConfig.getProperty("app.crypto.rsa-public-key");
        String rsaPrivateKeyFile = appConfig.getProperty("app.crypto.rsa-private-key");
        String desFirstKeyFile = appConfig.getProperty("app.crypto.des-first-key");
        String desSecondKeyFile = appConfig.getProperty("app.crypto.des-second-key");
        String desThirdKeyFile = appConfig.getProperty("app.crypto.des-third-key");
        String aesKeyFile = appConfig.getProperty("app.crypto.aes-key");
        String aesIvFile = appConfig.getProperty("app.crypto.aes-iv-key");
        String CLASS_PATH_STRING = "classpath:";
        String CLASS_PATH2_STRING = "classpath*:";
        if (!ChainProjectUtils.isEmpty(rsaPublicKeyFile) && !ChainProjectUtils.isEmpty(rsaPrivateKeyFile)) {
            rsaPublicKeyFile = getFilePath(rsaPublicKeyFile, CLASS_PATH_STRING, CLASS_PATH2_STRING);
            rsaPrivateKeyFile = getFilePath(rsaPrivateKeyFile, CLASS_PATH_STRING, CLASS_PATH2_STRING);
            cryptoFactoryBean.setRsaPublicKeyFilePath(rsaPublicKeyFile);
            cryptoFactoryBean.setRsaPrivateKeyFilePath(rsaPrivateKeyFile);
        }
        if (!ChainProjectUtils.isEmpty(desFirstKeyFile) && !ChainProjectUtils.isEmpty(desSecondKeyFile) &&
                !ChainProjectUtils.isEmpty(desThirdKeyFile)) {
            desFirstKeyFile = getFilePath(desFirstKeyFile, CLASS_PATH_STRING, CLASS_PATH2_STRING);
            desSecondKeyFile = getFilePath(desSecondKeyFile, CLASS_PATH_STRING, CLASS_PATH2_STRING);
            desThirdKeyFile = getFilePath(desThirdKeyFile, CLASS_PATH_STRING, CLASS_PATH2_STRING);
            cryptoFactoryBean.setDesFirstKeyFilePath(desFirstKeyFile);
            cryptoFactoryBean.setDesSecondKeyFilePath(desSecondKeyFile);
            cryptoFactoryBean.setDesThirdKeyFilePath(desThirdKeyFile);
        }
        if (!ChainProjectUtils.isEmpty(aesKeyFile) && !ChainProjectUtils.isEmpty(aesIvFile)) {
            aesKeyFile = getFilePath(aesKeyFile, CLASS_PATH_STRING, CLASS_PATH2_STRING);
            aesIvFile = getFilePath(aesIvFile, CLASS_PATH_STRING, CLASS_PATH2_STRING);
            cryptoFactoryBean.setAesKeyFilePath(aesKeyFile);
            cryptoFactoryBean.setAesIvFilePath(aesIvFile);
        }
        return cryptoFactoryBean;
    }

    private String getFilePath(String s, String CLASS_PATH_STRING, String CLASS_PATH2_STRING) {
        String classpath = null;
        if (s.startsWith(CLASS_PATH_STRING) || s.startsWith(CLASS_PATH2_STRING)) {
            //将jar包内的文件写到jar包外，才能获取到文件的路径
            s = s.replaceAll(CLASS_PATH_STRING, "");
            s = writeFileIntoTempDir(s);
        }
        return s;
    }

    private String writeFileIntoTempDir(String s) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //写在jar包外，以便读取
        FileOutputStream os = null;
        InputStream in = null;
        try {
            File logs = new File("logs" + File.separator + "temp");
            if (logs.exists() && logs.isDirectory()) {
                long lastModified = logs.lastModified();
                long nowtime = new Date().getTime();
                //10分钟
                if (nowtime - lastModified > 1000 * 60 * 10)
                    FileDirectoryUtils.emptyDirectory(logs);
            }
            if (!logs.exists()) {
                logs.mkdirs();
            }
            boolean isJar = false;
            //非Jar内，常规读取
            in = this.getClass().getClassLoader().getResourceAsStream(File.separator + s);
            if (in == null) {
                //Jar内，让Spring来读取
                in = resolver.getResource(File.separator + s).getInputStream();
                isJar = true;
            }
            if (isJar) {
                File f = new File("logs" + File.separator + "temp" + File.separator + RandomStringUtils.generateString(10));
                os = new FileOutputStream(f);
                byte[] buf = new byte[8 * 1024];
                int len = -1;
                while ((len = in.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                s = f.getAbsolutePath();
            } else {
                Resource res = resolver.getResource(File.separator + s);
                s = res.getFile().getAbsolutePath();
            }
        } catch (IOException e) {
            logger.error("!!! *** IO EXCEPTION, please check if this program has " +
                    "the permission to write or read on disk at your system *** !!!");
            throw new ChainProjectRuntimeException(e);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("io exception", e);
                }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("io exception", e);
                }
            }
        }
        return s;
    }
}
