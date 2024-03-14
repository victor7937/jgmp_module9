package com.epam.victor.exchanger.repository.exception;

public class JsonFileReadException extends RuntimeException{

    public JsonFileReadException(String message) {
        super(message);
    }

    public JsonFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
