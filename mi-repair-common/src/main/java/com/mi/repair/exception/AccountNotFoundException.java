package com.mi.repair.exception;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/20 12:32
 */
public class AccountNotFoundException extends BaseException{
    public AccountNotFoundException(){}

    public AccountNotFoundException(String msg){
        super(msg);
    }
}
