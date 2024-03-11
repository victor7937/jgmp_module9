package com.epam.victor.deadlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DeadlockTask {

    private final Object WRITE_LOCK = new Object();
    private final Object SUM_LOCK = new Object();
    private final Object NORM_LOCK = new Object();

    public void executeDeadlockTask(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);


        List<Integer> list = new ArrayList<>();


        Future<?> futureWriter = executorService.submit(() ->{
            boolean running = true;
            while (running){
                synchronized (SUM_LOCK){
                    synchronized (NORM_LOCK) {
                        synchronized (WRITE_LOCK){
                            RandomNumberWriter.writeNumber(list);
                        }
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    running = false;
                }
            }
        });

        Future<?> futureSum = executorService.submit(() -> {
            boolean running = true;
            while (running) {
                synchronized (SUM_LOCK) {
                    SumCalculator.countSum(list);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    running = false;
                }
            }
        });

        Future<?> futureNorm = executorService.submit(() -> {
            boolean running = true;
            while (running) {
                synchronized (NORM_LOCK) {
                    NormCalculator.countNorm(list);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    running = false;
                }
            }
        });

        executorService.shutdown();

        try {
            futureWriter.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException e) {
            futureWriter.cancel(true);
        } catch (ExecutionException e){
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("Task was canceled");
            futureSum.cancel(true);
            futureNorm.cancel(true);
        }
    }

}
