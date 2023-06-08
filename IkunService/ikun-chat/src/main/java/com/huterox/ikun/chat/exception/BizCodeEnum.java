package com.huterox.ikun.chat.exception;

/***
 * 错误码和错误信息定义类
 * 这里的错误码是20000开头的
 */
public enum BizCodeEnum {

    UNKNOW_EXCEPTION(20000,"系统未知异常"),
    VAILD_EXCEPTION(20001,"参数格式校验失败"),

    BAD_LOGIN_PARAMS(20014,"请求异常！触发5次以上账号将保护性封禁"),
    BAD_TOKEN(20015,"token校验失败"),
    NOT_LOGIN(20011,"用户未登录");

    private int code;
    private String msg;
    BizCodeEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
