package com.huterox.common.holeAnnotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuizViewNumberUp {

    //传入模式
    String mode() default "";
}
