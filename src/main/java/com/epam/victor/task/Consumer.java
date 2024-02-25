package com.epam.victor.task;

public class Consumer {

    private MessageQueue queue;
    private volatile boolean running = true;

    private Object lock;

    private Thread thread;

    private int messageReceivedCount = 0;

    public Consumer(MessageQueue queue) {
        this.queue = queue;
        this.lock = queue.getLock();
    }

    public void start(){
        thread = new Thread(() -> {
            while (running){
                try {
                    consume();
                    Thread.sleep(2000);
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


    public boolean isRunning() {
        return running;
    }

   public void stop(){
        running = false;
   }

    public int getMessageReceivedCount() {
        return messageReceivedCount;
    }
}
