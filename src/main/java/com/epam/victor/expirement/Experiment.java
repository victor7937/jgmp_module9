package com.epam.victor.expirement;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class Experiment {

    private volatile int indexCounter = 0;
    private final Random random = new Random();


    public void doExperiment(Map<Integer, Integer> map){

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        Future<?> addTaskFuture = executorService.submit(() -> {
            boolean running = true;
            while (running) {
                try {
                    addRandomElement(map);
                    Thread.sleep(10);
                } catch (ConcurrentModificationException | InterruptedException e) {
                    System.out.println(e);
                    running = false;
                }
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("Thread.currentThread().isInterrupted()");
                    running = false;
                }
            }
        });

        Future<?> isCmeFuture = executorService.submit(()->{
            boolean running = true;
            while (running){
                try {
                    Thread.sleep(100);
                    int sum = countSum(map);
                    System.out.println("sum: " + sum);
                }
                catch (ConcurrentModificationException | InterruptedException e) {
                    System.out.println(e);
                    running = false;
                }
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("Thread.currentThread().isInterrupted()");
                    running = false;
                }
                System.out.println(map);
            }
        });

        executorService.shutdown();

        try {
           isCmeFuture.get();
           addTaskFuture.cancel(true);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

//        try {
//            executorService1.awaitTermination(2, TimeUnit.MINUTES);
//            executorService2.awaitTermination(2, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


    }

    private void addRandomElement(Map<Integer, Integer> map){
        int number = random.nextInt(100);
        map.put(indexCounter++, number);
        System.out.println(indexCounter + " : " + number + " was added");
    }

    private int countSum(Map<Integer, Integer> map) throws InterruptedException {
//        int sum = 0;
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()){
//            sum += entry.getValue();
//
//            //Thread.sleep(100);
//        }
//        return sum;
        return map.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }


}
