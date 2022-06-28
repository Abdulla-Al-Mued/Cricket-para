package com.example.cricketpara.Algorithms;

import java.text.DecimalFormat;

public class Economy {

    private static DecimalFormat df = new DecimalFormat("0.00");

    public static String bowlerEconomy(int run , String over){

        double eco;

        eco = run / Double.parseDouble(over);


        return String.valueOf(df.format(eco));
    }

}
