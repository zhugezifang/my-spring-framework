package com.jd.xq.spring.annotation;

/**
 * @author duanxiaoqiu
 * @Date 2019-12-27 17:05
 **/
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//注解处理在运行时刻
@Target({ElementType.FIELD,ElementType.METHOD})//对字段和方法使用注解
public @interface YhdResource {
    String value() default "";//注解里面只能声明属性，不能声明方法，声明属性的方式比较特殊：
}
