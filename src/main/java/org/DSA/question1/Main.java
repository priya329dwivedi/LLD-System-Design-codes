package org.DSA.question1;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static int solve(String[][] scores){
        Map<String,int[]> mp= new HashMap<>();
        for(String[] score: scores){
            String name= score[0];
            int sc = Integer.parseInt(score[1]);
            mp.putIfAbsent(name,new int[]{0,0});
            int[] val= mp.get(name);
            val[0]+= sc;
            val[1]+=1;
        }
        int maxValue = Integer.MIN_VALUE;
        for(int[] m: mp.values()){
            int count= m[1];
            int sum= m[0];
            int avg= floorDiv(sum,count);
            maxValue= Integer.max(maxValue,avg);
        }
        return maxValue;
    }
    public static int floorDiv(int sum,int count){
        int avg;
        if(sum>=0){
            avg=sum/count;
            return avg;
        }
        avg= (sum-(count-1))/count;
        return avg;
    }
    public static void main(String[] args) {
        // TODO: DSA Question 1
        String[][] scores = {{"Bobby", "87"},
                {"Charles", "100"},
                {"Eric", "64"},
                {"Charles", "22"},
                {"Alice", "-10"},
                {"Alice", "-20"}};
        int ans= solve(scores);
        System.out.println(ans);
    }
}
