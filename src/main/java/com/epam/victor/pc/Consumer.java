package com.epam.victor.pc;

public class Consumer extends Thread {

    private MessageQueue queue;

    private Object lock;

    private boolean running = true;


    private int messageReceivedCount = 0;

    private int messageLimit;

    public Consumer(MessageQueue queue, int messageLimit) {
        this.queue = queue;
        this.messageLimit = messageLimit;
        this.lock = queue.getLock();
    }


    @Override
    public void run() {
        while (running){
            try {
                consume();
                Thread.sleep(2000);
                if (messageLimit == messageReceivedCount){
                    running = false;
                    System.out.println("Max messages received " + messageLimit);
                }
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

    private void consume() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                System.out.println("Queue is empty, letting producer create the item");
                lock.wait();
            }
            Message message = queue.poll();
            messageReceivedCount++;
            printMessage(message);
            System.out.println("Queue size is " + queue.getSize());
            lock.notify();
        }

    }

    private void printMessage(Message message){
        if (message.getTopic() == 1){
            System.out.println("Message of topic 1 was received: " + message.getText());
        }
        if (message.getTopic() == 2){
            System.out.println("Message of topic 2 was received: " + message.getText());
        }
    }


    public int getMessageReceivedCount() {
        return messageReceivedCount;
    }


    public int getMessageLimit() {
        return messageLimit;
    }

    public void setMessageLimit(int messageLimit) {
        this.messageLimit = messageLimit;
    }
}
