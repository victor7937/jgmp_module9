package com.epam.victor.exchanger.repository.exception;

public class JsonFileNotCreatedException extends RuntimeException{
    public JsonFileNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
