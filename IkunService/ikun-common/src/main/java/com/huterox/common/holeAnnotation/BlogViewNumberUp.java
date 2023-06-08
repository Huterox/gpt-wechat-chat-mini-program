package com.huterox.common.holeAnnotation;

import java.lang.annotation.*;

/**
 * 传递两个参数
 * 1. 当前的前缀，这个可以不用传入
 * 2. 传入模式，支持的模式有 pv,cv,fv,tv(博文全部支持，其余的不支持fv)
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BlogViewNumberUp {

    //传入模式
    String mode() default "";
}
