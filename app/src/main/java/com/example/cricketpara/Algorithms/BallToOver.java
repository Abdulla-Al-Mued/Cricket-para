package com.example.cricketpara.Algorithms;

public class BallToOver {


    public static String convertBallToOver(int n){

        int a,b,c;

        a = n/6;
        b = a * 6;
        c = n - b;

        String over = a+"."+c;

        return over;
    }

}
