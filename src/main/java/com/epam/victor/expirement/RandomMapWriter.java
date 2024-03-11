package com.epam.victor.expirement;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomMapWriter {

    private static final Random random = new Random();

    public static void addRandomElement(Map<Integer, Integer> map, AtomicInteger indexCounter){
        int number = random.nextInt(10);
        map.put(indexCounter.incrementAndGet(), number);
        System.out.println(indexCounter + " : " + number + " was added");
    }
}
