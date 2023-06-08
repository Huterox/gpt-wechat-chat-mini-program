package com.huterox.common.exception;

/**
 * 校验用户登录时，参数不对的情况，此时可能是恶意爬虫
 * */
public class BadLoginParamsException extends RuntimeException{
    public BadLoginParamsException(){}
    public BadLoginParamsException(String message){
        super(message);
    }

}
