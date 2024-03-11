package com.epam.victor.deadlocks;


import java.util.List;

public class NormCalculator {

    public static double countNorm(List<Integer> list) {
        int squareSum = list.stream()
                .mapToInt(i -> i * i)
                .sum();
        double norm = Math.sqrt(squareSum);
        System.out.println("Norm = " + norm);
        return norm;
    }
}
