package com.betmanager.exception;

public class UserAlreadyExist extends RuntimeException{

    public UserAlreadyExist(String message) {
        super(message);
    }
    public UserAlreadyExist(){}
}
