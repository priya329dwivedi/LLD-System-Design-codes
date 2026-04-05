/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package DSA.question2;

import lombok.Getter;

import java.util.*;

public class Main {
    @Getter
    public static class Student{
        String name;
        int avg;
        public Student(String name, int avg){
            this.avg=avg;
            this.name=name;
        }
    }
    public static List<String> topK(String[][] scores,int k){
        PriorityQueue<Student> pq= new PriorityQueue<>((a,b)->Double.compare(a.getAvg(),b.getAvg()));
        Map<String,int[]> mp= new HashMap<>();
        for(String[] str:scores){
            String name= str[0];
            int sum = Integer.parseInt(str[1]);
            mp.putIfAbsent(name,new int[]{0,0});
            int[] val= mp.get(name);
            val[0]+=sum;
            val[1]+=1;
        }
        for(Map.Entry<String,int[]> entry: mp.entrySet()){
            String name= entry.getKey();
            int[] val= entry.getValue();
            int avg= floorDiv(val[0],val[1]);
            Student student= new Student(name,avg);
            pq.offer(student);
            if(pq.size()>k){
                pq.poll();
            }
        }
        List<String> students = new ArrayList<>();
        while(!pq.isEmpty()){
            Student student = pq.poll();
            students.add(student.getName());
        }
        Collections.reverse(students);
        return students;
    }
    public static int floorDiv(int sum,int count){
        if(sum>=0){
            return sum/count;
        }
        return (sum-(count-1))/count;
    }
    public static void main(String[] args) {
        String[][] scores = {{"Bobby", "17"},
                {"Charles", "100"},
                {"Eric", "64"},
                {"Charle", "22"},
                {"Alice", "-10"},
                {"Alice", "1000"}};
        List<String> students=topK(scores, 2);
        System.out.println(students);

    }
}
