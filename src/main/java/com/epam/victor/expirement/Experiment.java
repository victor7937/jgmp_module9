package com.epam.victor.expirement;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Experiment {

    private AtomicInteger indexCounter = new AtomicInteger(0);

    public void doExperiment(Map<Integer, Integer> map, int secondsBeforeCancel){

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletableFuture<?>[] addNumberFutures = IntStream.range(0, 5)
            .mapToObj(i -> CompletableFuture.runAsync(() -> {
                boolean running = true;
                while (running) {
                    try {
                        RandomMapWriter.addRandomElement(map, indexCounter);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                        running = false;
                    }
                }
            }, executorService)).toArray(CompletableFuture[]::new);


        CompletableFuture<Void> addNumberFuturesCombine = CompletableFuture.allOf(addNumberFutures);

//        Future<?> addTaskFuture =

        Future<?> sumTaskFuture = executorService.submit(() -> {
            boolean running = true;
            while (running) {
                try {
                    Thread.sleep(100);
                    int sum = MapSumCalculator.countSum(map);
                    System.out.println("sum: " + sum);
                } catch (InterruptedException e) {
                    System.out.println(e);
                    running = false;
                }
                System.out.println(map);
            }
        });

        executorService.shutdown();

        try {
           sumTaskFuture.get(secondsBeforeCancel, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new ExperimentException(e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof ConcurrentModificationException){
                System.out.println("ConcurrentModificationException was thrown");
                e.printStackTrace(System.out);
                throw new ExperimentConcurrentModificationException(e);
            }
            throw new ExperimentException(e);
        } catch (TimeoutException e) {
            System.out.println("Experiment timeout elapsed. No ConcurrentModificationException was thrown");
        } finally {
            addNumberFuturesCombine.cancel(true);
        }
    }

}
