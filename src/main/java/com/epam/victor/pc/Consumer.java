package com.epam.victor.pc;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer{

    private MessageQueue queue;

    private boolean running = true;

    private AtomicInteger messageReceivedCount = new AtomicInteger(0);

    private int messageLimit;

    public Consumer(MessageQueue queue, int messageLimit) {
        this.queue = queue;
        this.messageLimit = messageLimit;
    }

    public Runnable getRun() {
        return () ->{
            while (running){
                try {
                    consume();
                    Thread.sleep(2000);
                    if (messageReceivedCount.get() == messageLimit){
                        running = false;
                        System.out.println("Max messages received " + messageLimit);
                    }
                } catch (InterruptedException e) {
                    running = false;
                    System.out.println("Thread was interrupted");
                }
            }
        };
    }

    private void consume() throws InterruptedException {
        Message message = queue.poll();
        messageReceivedCount.incrementAndGet();
        System.out.println("Messages received " + messageReceivedCount.get());
        printMessage(message);

    }

    private void printMessage(Message message){
        if (message.getTopic() == 1){
            System.out.println("Topic 1 was assigned to message : " + message.getText());
        }
        if (message.getTopic() == 2){
            System.out.println("Topic 2 was assigned to message : " + message.getText());
        }
    }
}
