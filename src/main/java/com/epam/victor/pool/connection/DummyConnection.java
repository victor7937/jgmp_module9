package com.epam.victor.pool.connection;

import lombok.Data;
import lombok.ToString;

@Data
public class DummyConnection implements Connection{

    private boolean opened;

    @Override
    public void close() {
        opened = false;
    }

    @Override
    public boolean isClosed() {
        return !opened;
    }

    @Override
    public void open() {
       opened = true;
    }

    @Override
    public boolean isOpened() {
        return opened;
    }
}
