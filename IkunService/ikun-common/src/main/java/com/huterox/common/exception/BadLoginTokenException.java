package com.huterox.common.exception;

public class BadLoginTokenException extends RuntimeException{
    public BadLoginTokenException(){}
    public BadLoginTokenException(String message){
        super(message);
    }
}
