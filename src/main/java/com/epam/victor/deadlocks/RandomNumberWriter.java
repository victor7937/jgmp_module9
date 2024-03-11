package com.epam.victor.deadlocks;

import java.util.List;
import java.util.Random;

public class RandomNumberWriter {

    public static void writeNumber(List<Integer> list){
        Random random = new Random();
        int num = random.nextInt(10) + 1;
        list.add(num);
        System.out.println("Add new number to list: " + num);
    }
}
