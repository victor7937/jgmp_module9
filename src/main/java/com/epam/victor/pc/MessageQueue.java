package com.epam.victor.pc;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private final Queue<Message> queue = new LinkedList<>();
    private final int maxSize;
    private final Object QUEUE_LOCK = new Object();

    MessageQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(Message message) throws InterruptedException {
        synchronized (QUEUE_LOCK){
            while(queue.size() >= maxSize){
                System.out.println("Queue is full. Letting consumer take the item");
                QUEUE_LOCK.wait();
            }
            queue.add(message);
            QUEUE_LOCK.notify();
        }

    }

    public Message poll() throws InterruptedException {
        synchronized (QUEUE_LOCK){
            while(queue.isEmpty()){
                System.out.println("Queue is empty, letting producer create the item");
                QUEUE_LOCK.wait();
            }
            Message message = queue.poll();
            System.out.println(message + " was received");
            System.out.println("Queue size is " + queue.size());
            QUEUE_LOCK.notify();
            //notifyIsNotFull();
            return message;
        }
    }

}
