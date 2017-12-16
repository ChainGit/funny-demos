package com.chain.project.common.validator;

import com.chain.project.common.domain.ApiUsage;
import com.chain.utils.ReflectionUtils;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.common.exception.ChainProjectRuntimeException;
import com.chain.project.common.exception.ErrorCode;
import com.chain.project.common.utils.ChainProjectUtils;

import java.lang.reflect.Method;

/**
 * 验证JsonMap中所需的参数是否为空
 */
public class JsonMapValidator {

    /**
     * 测试方法，只适用于xxxx(JsonMap map)，需要配合ApiUsage注解使用。
     * 验证ApiUsage注解里的param和paramType(可选)是否为null
     */
    public static JsonMap valid(JsonMap map) {
        StackTraceElement[] stacks = (new Throwable()).getStackTrace();
        if (stacks == null || stacks.length < 2)
            return map;
        //获得方法的调用者
        StackTraceElement lastStack = stacks[1];
        String methodName = lastStack.getMethodName();
        String className = lastStack.getClassName();
        Class clz = null;
        try {
            clz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ChainProjectRuntimeException(e);
        }
        Method lastMethod = ReflectionUtils.getDeclaredMethod(clz, methodName, JsonMap.class);
        ApiUsage annotation = (ApiUsage) ReflectionUtils.getDeclaredAnnotation(lastMethod, ApiUsage.class);
        if (annotation == null)
            return map;
        String[] param = annotation.param();
        if (ChainProjectUtils.isEmpty(param))
            return map;
        Class[] classes = annotation.paramType();
        //classes默认为{}，长度为0，但不是null
        if (classes != null && classes.length == param.length)
            return valid(map, param, classes);
        else
            return valid(map, param);
    }

    public static JsonMap valid(JsonMap map, String... keys) {
        if (ChainProjectUtils.isEmpty(map) || ChainProjectUtils.isEmpty(keys))
            return map;
        for (int i = 0; i < keys.length; i++) {
            //key为null，默认值也为null
            String key = keys[i];
            if (ChainProjectUtils.isEmpty(key))
                throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", key index " + i + " is empty or null");
            Object o = map.get(key);
            if (null == o)
                throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", the value of map where key '" + key + "' is null");
            if (o instanceof String) {
                String s = (String) o;
                if (ChainProjectUtils.isEmpty(s))
                    throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", the STRING value of map where key '" + key + "' " +
                            "is null or empty");
            }
        }
        return map;
    }

    public static JsonMap valid(JsonMap map, String[] keys, Class[] clzs) {
        if (ChainProjectUtils.isEmpty(map) || ChainProjectUtils.isEmpty(keys)
                || ChainProjectUtils.isEmpty(clzs) || keys.length != clzs.length)
            return map;
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (ChainProjectUtils.isEmpty(key))
                throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", key index " + i + " is empty or null");
            Class<?> clz = clzs[i];
            if (ChainProjectUtils.isEmpty(clz))
                throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", class index " + i + " is empty or null");
            Object o = map.get(key);
            if ((null == o) || (!o.getClass().equals(clz)))
                throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", the value of map where key '" + key + "' " +
                        "is null, or is not equals the class needed");
            if (o instanceof String) {
                String s = (String) o;
                if (ChainProjectUtils.isEmpty(s))
                    throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", the STRING value of map where key '" + key + "' " +
                            "is null or empty");
            }
        }
        return map;
    }


    public static JsonMap valid(JsonMap map, String key, Class<?> clz) {
        if (ChainProjectUtils.isEmpty(map))
            return map;
        if (ChainProjectUtils.isEmpty(key))
            throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", key is empty or null");
        if (ChainProjectUtils.isEmpty(clz))
            throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", class is empty or null");
        Object o = map.get(key);
        if ((null == o) || (!o.getClass().equals(clz)))
            throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", the value of map where key '" + key + "' " +
                    "is null, or is not equals the class needed");
        if (o instanceof String) {
            String s = (String) o;
            if (ChainProjectUtils.isEmpty(s))
                throw new ChainProjectRuntimeException("error: " + ErrorCode.PARAM_IN + ", the STRING value of map where key '" + key + "' " +
                        "is null or empty");
        }
        return map;
    }
}
