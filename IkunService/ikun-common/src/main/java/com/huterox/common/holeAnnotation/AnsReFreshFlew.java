package com.huterox.common.holeAnnotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnsReFreshFlew {
    String type() default "";
    String key() default "";
}
