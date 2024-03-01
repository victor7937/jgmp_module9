package com.epam.victor.pool.simple;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Pool that block when it has not any items or it full

 */
public class BlockingObjectPool {

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */

    //private Semaphore semaphore;
    private LinkedBlockingQueue<Object> objects;

    private ReentrantLock lock = new ReentrantLock();


    private int limit;

    public BlockingObjectPool(int limit) {
        this.limit = limit;
        initObjects();
    }


    private void initObjects(){
        objects = new LinkedBlockingQueue<>(limit);
        for (int i = 0; i < limit; i++){
            objects.offer(new PoolObject(i));
        }

    }


    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public Object get() throws InterruptedException {
        return objects.take();
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public void putBack(Object object) throws InterruptedException {
        objects.put(object);
    }

}
