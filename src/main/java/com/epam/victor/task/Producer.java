package com.epam.victor.task;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

public class Producer {
    private MessageQueue queue;
    private volatile boolean running = true;

    private Object lock;

    private Thread thread;

    public Producer(MessageQueue queue) {
        this.queue = queue;
        this.lock = queue.getLock();
    }

    public void start(){
        thread = new Thread(() -> {
            while (running){
                try {
                    produce();
                } catch (InterruptedException e) {
                    throw new ConsumerInterruptedException(e);
                }
            }
        });
        thread.start();

    }


    public void join(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new ConsumerInterruptedException(e);
        }
    }

    private void produce() throws InterruptedException {
        synchronized (lock) {
            while (queue.isFull()) {
                System.out.println("Queue is full, letting consumer take the item");
                lock.wait();
            }
            Message message = generateRandom();
            queue.add(message);
            //Thread.sleep(1000);
            System.out.println("New message was created: " + message);
            lock.notify();
        }
    }

    private Message generateRandom(){
        Random random = new Random();
        int randomTopic = random.nextInt(2) + 1;
        return new Message(UUID.randomUUID().toString(), randomTopic);
    }

    public boolean isRunning() {
        return running;
    }

    public void stop(){
        running = false;
    }
}
