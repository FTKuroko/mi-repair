package com.mi.repair.exception;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/14 15:50
 */
public class BaseException extends RuntimeException{
    public BaseException(){}

    public BaseException(String msg){super(msg);}
}
