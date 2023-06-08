package com.huterox.common.holeAnnotation;

import java.lang.annotation.*;

/**
 *负责完成后端接口的一个调用
 * */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminAuthority {
    String value() default "";
}
