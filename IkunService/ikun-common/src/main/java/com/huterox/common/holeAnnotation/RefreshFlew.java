package com.huterox.common.holeAnnotation;


import java.lang.annotation.*;

/**
 * 这个主要是用来处理流量数据的
 * 主要是将redis当中的流量数据给到返回的结果当中（因为Redis当中的结果才是实时准确的）
 * 虽然我们这边应该是有接口来专门获取到我们的流量数据的，但是一些别的接口
 * 例如使用到了缓存技术的一些接口，这些保存的都不是实时的，因此必须进行处理
 *
 * */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshFlew {

    String type() default "";
    String key() default "";
}
