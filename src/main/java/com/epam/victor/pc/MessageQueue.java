package com.epam.victor.pc;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private int maxSize;
    private Queue<Message> queue = new LinkedList<>();

    private final Object lock = new Object();

    public MessageQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public Object getLock() {
        return lock;
    }

    public boolean isFull() {
        return queue.size() == maxSize;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void add(Message message) {
        queue.add(message);
    }

    public Message poll() {
        return queue.poll();
    }

    public Integer getSize() {
        return queue.size();
    }
}
