package com.epam.victor.exchanger.service.exception;

public class AccountAlreadyExistException extends RuntimeException{

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
