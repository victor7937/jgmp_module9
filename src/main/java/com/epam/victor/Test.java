package com.epam.victor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Test {

    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private static final Gson gsonInstance = new GsonBuilder().setDateFormat(YYYY_MM_DD_T_HH_MM_SS)
            .registerTypeAdapter(Map.class, new CustomMapSerializer()).create();



    public static void main(String[] args) throws ParseException, InterruptedException {
        //Date recordOrderDate = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS).parse("2024-02-24T12:30:02.000");

        for (int i = 0; i< 1000; i++){
            Date recordOrderDate = new Date();
            DefaultHitOrder defaultHitOrder = new DefaultHitOrder(1, recordOrderDate);
            System.out.println(gsonInstance.toJson(defaultHitOrder));
            Thread.sleep(1);
        }


    }

}
