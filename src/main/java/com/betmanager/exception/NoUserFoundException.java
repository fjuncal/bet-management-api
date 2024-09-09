package com.betmanager.exception;

public class NoUserFoundException extends RuntimeException {

    public NoUserFoundException(String message) {
        super(message);
    }

    public NoUserFoundException(){}
}
