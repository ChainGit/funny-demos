package com.chain.project.common.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要对handler方法的API接口描述，需要结合@RequestMapping一起使用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiUsage {

    /**
     * 接口的描述
     *
     * @return
     */
    String value() default "";

    /**
     * 必须传入的参数名称
     *
     * @return
     */
    String[] param() default {};

    /**
     * 必须传入的参数描述
     *
     * @return
     */
    String[] paramDesc() default {};

    /**
     * 必须传入的参数类型
     *
     * @return
     */
    Class[] paramType() default {};

    /**
     * 可选传入的参数名称
     *
     * @return
     */
    String[] optional() default {};

    /**
     * 可选传入的参数描述
     *
     * @return
     */
    String[] optionalDesc() default {};

    /**
     * 可选传入的参数类型
     *
     * @return
     */
    Class[] optionalType() default {};

    /**
     * 返回的参数名称
     *
     * @return
     */
    String[] result() default {};

    /**
     * 可能会返回也可能不返回的参数
     */
    String[] resultOptional() default {};

    /**
     * 返回的参数描述
     *
     * @return
     */
    String[] resultDesc() default {};

    /**
     * 返回的数据类型
     */
    Class[] resultType() default {};

    /**
     * 可能返回的参数描述
     *
     * @return
     */
    String[] resultOptionalDesc() default {};

    /**
     * 可能返回的数据类型
     */
    Class[] resultOptionalType() default {};
}
