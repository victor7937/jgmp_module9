package com.epam.victor.pool;

import com.epam.victor.expirement.ExperimentException;
import com.epam.victor.pool.connection.Connection;
import com.epam.victor.pool.connection.ConnectionPool;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ConnectionPoolTest {

    @Test
    void runConnectionPool(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        connectionPool.init(10,5);

        System.out.println(connectionPool.getAvailableConnectionsCount());

        List<Future<Connection>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Connection> future = executorService.submit(() -> {
                Thread.sleep(2000);
                Connection connection = connectionPool.getConnection();
                System.out.println("Connection taken " + connection);
                return connection;
            });
            futureList.add(future);
        }


        List<Connection> connections = futureList.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        System.out.println(connections);

        executorService.submit(() -> {
            System.out.println("Trying to get");
            Connection connection = connectionPool.getConnection();
            System.out.println("Got successfully " + connection);
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(10000);
                System.out.println("Release one");
                connectionPool.releaseConnection(connections.get(0));
                System.out.println("Connection released");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        connectionPool.destroy();

        System.out.println(connectionPool.getAvailableConnectionsCount());

    }
}
