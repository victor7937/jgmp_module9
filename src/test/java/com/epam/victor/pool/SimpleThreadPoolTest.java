package com.epam.victor.pool;

import com.epam.victor.pool.simple.BlockingObjectPool;
import com.epam.victor.pool.simple.PoolObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SimpleThreadPoolTest {

    @Test
    void simplePoolShouldBeExecuted(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        BlockingObjectPool blockingObjectPool = new BlockingObjectPool(10);

        List<Future<PoolObject>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<PoolObject> future = executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                    PoolObject object = (PoolObject) blockingObjectPool.get();
                    System.out.println("Object taken " + object);
                    return object;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            futureList.add(future);
        }


        List<PoolObject> objectList = futureList.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        System.out.println(objectList);

        executorService.submit(() -> {
            try {
                System.out.println("Trying to get");
                PoolObject object = (PoolObject)blockingObjectPool.get();
                System.out.println("Got successfully " + object);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(10000);
                System.out.println("Release one");
                blockingObjectPool.putBack(objectList.get(0));
                System.out.println("Object released");
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

    }

}
