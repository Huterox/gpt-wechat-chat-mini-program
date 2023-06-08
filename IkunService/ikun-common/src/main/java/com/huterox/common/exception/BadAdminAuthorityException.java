package com.huterox.common.exception;

public class BadAdminAuthorityException extends RuntimeException{
    public BadAdminAuthorityException(){}
    public BadAdminAuthorityException(String message){
        super(message);
    }

}
