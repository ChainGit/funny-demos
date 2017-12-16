package com.chain.project.common.intercepter;

import com.chain.project.config.AppConfig;
import com.chain.project.common.directory.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获得执行的时间
 */
public class MeasurementInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(MeasurementInterceptor.class);

    @Autowired
    private AppConfig appConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("s_startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("s_startTime");
        request.removeAttribute("s_startTime");
        long endTime = System.currentTimeMillis();
        //非生产环境才打印
        if (!Constant.PROD_MODE.equals(appConfig.getProperty("spring.profiles.active")))
            logger.info("Handling Time: " + (endTime - startTime));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}