package com.epam.victor.task;

public class Service {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(10);
        Consumer consumer = new Consumer(messageQueue);
        Producer producer = new Producer(messageQueue);
        consumer.start();
        producer.start();

        consumer.join();
        producer.join();

        if (consumer.getMessageReceivedCount() == 20){
            consumer.stop();
            producer.stop();
        }
    }
}
