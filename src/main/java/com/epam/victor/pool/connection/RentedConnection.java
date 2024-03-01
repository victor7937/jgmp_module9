package com.epam.victor.pool.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentedConnection implements Connection {

    private Connection connection;

    private boolean available;


    public RentedConnection(Connection connection){
        this.connection = connection;
        this.available = true;
    }

    @Override
    public void close() {
        connection.close();
    }

    @Override
    public boolean isClosed() {
        return connection.isClosed();
    }

    @Override
    public void open() {
        connection.open();
    }

    @Override
    public boolean isOpened() {
        return connection.isOpened();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
