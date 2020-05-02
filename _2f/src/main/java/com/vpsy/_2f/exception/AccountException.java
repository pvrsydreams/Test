package com.vpsy._2f.exception;

/**
 * @author punith
 * @date 2020-04-25
 * @description
 */
public class AccountException extends Exception {
    private String message;

    public AccountException(String message) {
        this.message = message;
    }
}
