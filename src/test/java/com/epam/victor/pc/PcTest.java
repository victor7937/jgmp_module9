package com.epam.victor.pc;

import org.junit.jupiter.api.Test;

public class PcTest {

    @Test
    void runPC(){
        MessageQueue messageQueue = new MessageQueue(10);
        Consumer consumer = new Consumer(messageQueue, 20);
        Producer producer = new Producer(messageQueue);
        consumer.start();
        producer.start();

        try {
            Thread.sleep(30000);
            producer.interrupt();
            consumer.join();
            producer.join();
        } catch (InterruptedException e) {
            throw new ProducerConsumerInterruptedException(e);
        }
    }
}
