package com.chain.project.common.domain;

import com.chain.project.common.utils.ChainProjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * Map的继承类，方便操作Json的Map对象
 * <p>
 * <p>
 * Jackson会在Converter里自动的对常见的基本数据类进行转换值。
 * 但注意{"id":1}和{"id":"1"}不是一个意思，后者只会转成String
 * <p>
 * 使用：
 * 1、getXxx为直接从JsonMap中读取值，比如使用getInteger()，若原先是int型的value值，
 * 那么能成功读出，如果原先是string型的value值则读取错误。
 * 2、getParsedXxx为从JsonMap中读取转换值，比如使用getParsedInteger，
 * 则会先将JsonMap中的值（无论是否是String，如果是null则转为"null"）转为String,然后再进行转换。
 *
 * @author Chain
 */
public class JsonMap extends HashMap<String, Object> {

    private static Logger logger = LoggerFactory.getLogger(JsonMap.class);

    public Object get(String key) {
        return super.get(key);
    }

    // TODO 如果需要接收的数据类型比较复杂，比如有BigInteger,BigDecimal等等
    // TODO 此时建议使用Map中原生的get(String key)返回未转换的Object进行单独处理
    // TODO 也可以request.getAttribute(Constant.JSON_MAP)获得原始的字符串
    public <T> T get(String key, Class<T> clz) {
        Object obj = get(key);
        if (obj != null) {
            Class<?> objClass = obj.getClass();
            if (objClass.equals(clz)) {
                return (T) obj;
            } else if (String.class.equals(clz)) {
                return (T) String.valueOf(obj);
            } else if (obj instanceof Number) {
                //jackson默认将int范围内的接收到的值转为int,long范围的是long,float同理
                //目前只能考虑常见的转换，建议实体类统一使用包装类
                if (obj instanceof Integer && clz.equals(Long.class)) {
                    Integer i = (Integer) obj;
                    Long lo = Long.valueOf(i.longValue());
                    return (T) lo;
                } else if (obj instanceof Float && clz.equals(Double.class)) {
                    Float f = (Float) obj;
                    Double lo = Double.valueOf(f.doubleValue());
                    return (T) lo;
                }
            }
            // FIXME 这里可能会出现转换异常
            try {
                return (T) obj;
            } catch (Exception e) {
                logger.error("convert exception", e);
                // throw new ChainProjectRuntimeException("map value of '" + key + "' convert to '" + clz.getClass() + "' fail");
                return null;
            }
        }
        return null;
    }

    //获取value类型为String的值，注意和parsedXxx的区别
    public String getString(String key) {
        return get(key, String.class);
    }

    public int getInteger(String key) {
        return get(key, Integer.class);
    }

    public float getFloat(String key) {
        return get(key, Float.class);
    }

    public long getLong(String key) {
        return get(key, Long.class);
    }

    public double getDouble(String key) {
        return get(key, Double.class);
    }

    public boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

    //本框架中约定时间以long型数与前端交互
    public Date getDate(String key) {
        long longDate = getLong(key);
        return new Date(longDate);
    }

    //------------------------------------------------------------------------------------------------//

    public boolean isString(Object obj) {
        return obj != null && obj instanceof String;
    }

    public int getParsedInteger(String key) {
        String s = getParsedString(key);
        int value = Integer.parseInt(s);
        return value;
    }


    public long getParsedLong(String key) {
        String s = getParsedString(key);
        long value = Long.parseLong(s);
        return value;
    }

    public float getParsedFloat(String key) {
        String s = getParsedString(key);
        float value = Float.parseFloat(s);
        return value;
    }

    public double getParsedDouble(String key) {
        String s = getParsedString(key);
        double value = Double.parseDouble(s);
        return value;
    }

    public boolean getParsedBoolean(String key) {
        String s = getParsedString(key);
        boolean value = Boolean.parseBoolean(s);
        return value;
    }

    public Date getParsedDate(String key) {
        long time = getParsedLong(key);
        return new Date(time);
    }

    //将接收key的value值均转换成String，但null除外
    public String getParsedString(String key) {
        Object obj = get(key);
        String s = null;
        if (isString(obj)) {
            s = (String) obj;
        } else {
            if (obj != null)
                s = String.valueOf(obj);
            // throw new ChainProjectRuntimeException("map value of '" + key + "' is not a string");
        }
        return s;
    }

    //将字符串形式的long转为日期
    public String getParsedFormatDateString(String key) {
        Date date = getParsedDate(key);
        return ChainProjectUtils.toFormatDateString(date);
    }

    //使用Java8的新时间API，也可以使用Joda-Time
    public String getFormatDateString(String key) {
        Date date = getDate(key);
        return ChainProjectUtils.toFormatDateString(date);
    }

    //Java8的新时间API之间是使用新API的类，通过Instant和旧的Date进行转换，DateTimeFormatter和旧的Format进行转换
    //新时间API的类均为无状态的，和String一样，是不可变对象，因此所有的操作均是返回新的对象
    public String getFormatDateString(String key, DateTimeFormatter dateTimeFormatter) {
        Date date = getDate(key);
        return ChainProjectUtils.toFormatDateString(date, dateTimeFormatter);
    }

    public String getParsedFormatDateString(String key, DateTimeFormatter dateTimeFormatter) {
        Date date = getParsedDate(key);
        return ChainProjectUtils.toFormatDateString(date, dateTimeFormatter);
    }

}
