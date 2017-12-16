package com.chain.project.base.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * controller基类,目前功能比较简单
 * <p>
 * 在handler层做log，service层抛出异常
 */
public abstract class BaseHandler {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

}