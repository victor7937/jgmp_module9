package com.epam.victor.task;

public class ConsumerInterruptedException extends RuntimeException{
    public ConsumerInterruptedException() {
    }

    public ConsumerInterruptedException(Throwable cause) {
        super(cause);
    }
}
