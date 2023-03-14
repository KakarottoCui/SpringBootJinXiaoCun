package com.it.aspect;


import java.lang.annotation.*;

/**
 * 自定义日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
