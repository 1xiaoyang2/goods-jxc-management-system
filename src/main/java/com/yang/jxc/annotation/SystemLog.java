package com.yang.jxc.annotation;

import java.lang.annotation.*;

/**
 * @author 李博洋
 * @date 2025/5/27
 * 系统日志注解
 * 被该注解标识的方法执行的时候会被AOP来拦截。然后记录相关的操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String value() default "";
}
