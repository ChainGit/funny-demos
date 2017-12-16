package com.chain.project.common.formatter;

import com.chain.project.common.exception.ChainProjectRuntimeException;
import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.common.directory.Constant;
import org.springframework.format.Formatter;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CarServerDateFormatter implements Formatter<Date> {

    public String print(Date object, Locale locale) {
        return null;
    }

    /**
     * 使用joda替代jdk自带的日期格式化类，因为 DateFormat 和 SimpleDateFormat 类不都是线程安全的
     * 确保不会在多线程状态下使用同一个 DateFormat 或者 SimpleDateFormat 实例
     * 如果多线程情况下需要访问同一个实例，那么请用同步方法
     * 可以使用 joda-time 日期时间处理库来避免这些问题，如果使用java 8, 请切换到 java.time包
     * 你也可以使用 commons-lang 包中的 FastDateFormat 工具类
     * 另外你也可以使用 ThreadLocal 来处理这个问题
     */
    public Date parse(String text, Locale locale) {
        Date date = null;
        if (ChainProjectUtils.isEmpty(text))
            return date;
        try {
            date = ChainProjectUtils.parseDateFormString(text);
        } catch (Exception e1) {
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constant.DATE_PATTERN);
                date = ChainProjectUtils.parseDateFormString(text, dateTimeFormatter);
            } catch (Exception e2) {
                try {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constant.TIME_PATTERN);
                    date = ChainProjectUtils.parseDateFormString(text, dateTimeFormatter);
                } catch (Exception e3) {
                    throw new ChainProjectRuntimeException("can not parse string to date/time/datetime");
                }
            }
        }
        return date;
    }

}