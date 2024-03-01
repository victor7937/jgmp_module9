package com.epam.victor.pool.connection;

public class ConnectionManager {
    public static Connection getConnection(){
        DummyConnection dummyConnection = new DummyConnection();
        dummyConnection.open();
        return dummyConnection;
    }
}
