package com.chain.project.common.intercepter;

import com.chain.project.common.directory.Constant;
import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检测请求，屏蔽/test/
 */
public class MonitorRequestInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(MonitorRequestInterceptor.class);
    @Autowired
    private AppConfig appConfig;

    private static String[] permits;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestPath = httpServletRequest.getServletPath();

        String mode = appConfig.getProperty("spring.profiles.active");

        if (permits == null) {
            String dirs = appConfig.getProperty("app.api-url.package");
            if (ChainProjectUtils.isEmpty(dirs) || "*".equals(dirs)) {
                permits = new String[]{};
            } else {
                permits = dirs.split(",");
            }
        }

        //如果是生产环境，过滤掉test
        if (requestPath != null && requestPath.startsWith("/test") && Constant.PROD_MODE.equals(mode))
            return false;


        //过滤不在permitted之内的请求，直接访问根目录"/"除外
        if (requestPath != null && !requestPath.equals("/") && permits.length > 0) {
            boolean isPermitted = false;
            for (String s : permits) {
                if (requestPath.startsWith("/" + s)) {
                    isPermitted = true;
                    break;
                }
            }
            if (!isPermitted)
                return false;
        }

        //非生产环境会记录日志
        if (!Constant.PROD_MODE.equals(mode)) {
            String contextPath = httpServletRequest.getContextPath();
            String requestQueryString = httpServletRequest.getQueryString();
            String fullUrlPath = contextPath + (requestPath == null ? "" : requestPath) +
                    ", queryString: " + (requestQueryString == null ? "empty" : requestQueryString);
            logger.info("Request URL: " + fullUrlPath);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) throws Exception {

    }
}
