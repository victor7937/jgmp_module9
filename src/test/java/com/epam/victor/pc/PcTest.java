package com.epam.victor.pc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PcTest {

    @Test
    void runPC(){
        MessageQueue messageQueue = new MessageQueue(10);
        Consumer consumer = new Consumer(messageQueue, 20);
        Producer producer = new Producer(messageQueue);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<?> futureP = executorService.submit(producer.getRun());
        executorService.submit(consumer.getRun());

        executorService.shutdown();


        try {
            Thread.sleep(30000);
            futureP.cancel(true);
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new ProducerConsumerInterruptedException(e);
        }
    }
}
