package com.epam.victor.exchanger.service.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
    private final Map<String, Lock> lockMap = new HashMap<>();

    private final Map<String, Integer> releaseMap = new HashMap<>();

    private final Lock managerLock = new ReentrantLock();


    private Lock getLock(String id){
        managerLock.lock();
        try {
            Lock lock = lockMap.get(id);
            if (lock == null) {
                lock = new ReentrantLock();
                lockMap.put(id, lock);
                releaseMap.put(id, 0);
            }
            releaseMap.put(id, releaseMap.get(id) + 1);
            return lock;
        } finally {
            managerLock.unlock();
        }
    }

    private void releaseLock(String id){
        managerLock.lock();
        try {
            int value = releaseMap.get(id) - 1;
            releaseMap.put(id, value);
            if (value == 0){
                releaseMap.remove(id);
                lockMap.remove(id);
            }
        } finally {
            managerLock.unlock();
        }
    }

    public LockPair getPair(String idFirst, String idSecond){
        boolean compareCondition = idFirst.compareTo(idSecond) < 0;
        String firstId = compareCondition ? idFirst : idSecond;
        String secondId = compareCondition ? idSecond : idFirst;

        Lock firstLock = getLock(firstId);
        Lock secondLock = getLock(secondId);

        return new LockPair(firstLock, secondLock, firstId, secondId);
    }

    public void lockFirst(LockPair lockPair){
        lockPair.getFirstLock().lock();
    }

    public void lockSecond(LockPair lockPair){
        lockPair.getSecondLock().lock();
    }

    public void unlockAndReleaseFirst(LockPair lockPair){
        lockPair.getFirstLock().unlock();
        releaseLock(lockPair.getFirstId());
    }

    public void unlockAndReleaseSecond(LockPair lockPair){
        lockPair.getSecondLock().unlock();
        releaseLock(lockPair.getSecondId());
    }

}
