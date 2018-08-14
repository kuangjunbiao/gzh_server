package com.gaoan.forever.exception;

/**
 * 基础异常
 * Created by three on 2017/4/13.
 */
public class BaseException extends RuntimeException{

    public static final String NO_USER = "用户没有登陆";

    /**
     *
     * @param message 异常信息
     */
   public BaseException(String message){
            super(message);
    }
}
