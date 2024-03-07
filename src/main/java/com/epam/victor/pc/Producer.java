package com.epam.victor.pc;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class Producer{
    private MessageQueue queue;

    private boolean running = true;

    public Producer(MessageQueue queue) {
        this.queue = queue;
    }

    public Runnable getRun() {
        return () -> {
            while (running){
                try {
                    Thread.sleep(100);
                    produce();
                } catch (InterruptedException e) {
                    running = false;
                    System.out.println("Thread was interrupted");
                }
            }
        };
    }

    private void produce() throws InterruptedException {
        Message message = generateRandom();
        System.out.println("New message was created: " + message);
        queue.add(message);
    }

    private Message generateRandom(){
        Random random = new Random();
        int randomTopic = random.nextInt(2) + 1;
        return new Message(UUID.randomUUID().toString(), randomTopic);
    }


}
