package com.software.dm.swallow.stormy.redis;

public class Test1 {

    public static void main(String[] args) {
        long minNum = 13300000000l;
        long count = 100000000;
        long lots = count / 500;

        for (int i = 1; i <= 500; i++) {
            long maxNum = minNum + lots;


            System.out.println(minNum + "=" + maxNum);

            minNum = maxNum;
            maxNum += lots;
        }


    }
}
