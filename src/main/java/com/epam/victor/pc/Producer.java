package com.epam.victor.pc;

import java.util.Random;
import java.util.UUID;

public class Producer extends Thread {
    private MessageQueue queue;

    private Object lock;

    private boolean running = true;

    private Thread thread;

    public Producer(MessageQueue queue) {
        this.queue = queue;
        this.lock = queue.getLock();
    }


    @Override
    public void run() {
        while (running){
            try {
                produce();
                if (Thread.currentThread().isInterrupted()){
                    running = false;
                    System.out.println("Thread was interrupted");
                }
            } catch (InterruptedException e) {
               running = false;
               System.out.println("Thread was interrupted");
            }
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


}
