package com.epam.victor.pool.connection;

public class ConnectionPoolException extends RuntimeException{
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
