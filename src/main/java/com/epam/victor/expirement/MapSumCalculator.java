package com.epam.victor.expirement;

import java.util.Map;

public class MapSumCalculator {
    public static int countSum(Map<Integer, Integer> map) {
//        int sum = 0;
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()){
//            sum += entry.getValue();
//
//            //Thread.sleep(100);
//        }
//        return sum;
        return map.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
