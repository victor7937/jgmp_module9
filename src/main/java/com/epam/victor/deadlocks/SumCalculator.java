package com.epam.victor.deadlocks;

import java.util.List;

public class SumCalculator {
    
    public static int countSum(List<Integer> list){
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
        return sum;
    }
}
