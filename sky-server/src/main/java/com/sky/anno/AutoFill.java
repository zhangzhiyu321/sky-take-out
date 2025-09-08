package com.sky.anno;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 这个注解只用在方法上
@Target(ElementType.METHOD)
// 运行时通过反射拿到值
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    // 枚举值
    OperationType value();
}
