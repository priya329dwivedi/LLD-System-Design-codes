/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package DSA.question4;

import java.util.*;

public class Main {
    public static String frequentIp(String[] lines){
        Map<String,Integer> mp= new HashMap<>();
        for(int i=0;i<lines.length;i++){
            String ip= lines[i].split(" ")[0];
            mp.put(ip,mp.getOrDefault(ip,0)+1);
        }
        int maxFrequent = 0;
        for(int m:mp.values()){
            maxFrequent= Math.max(maxFrequent,m);
        }
        List<String> ans= new ArrayList<>();
        for(Map.Entry<String,Integer> entry: mp.entrySet()){
            if(entry.getValue().equals(maxFrequent)){
                ans.add(entry.getKey());
            }
        }
        Collections.sort(ans);
        return String.join(",",ans);
    }
    public static void main(String[] args) {
        String[] lines = {"10.0.0.1 - GET 2020-08-24",
                "10.0.0.1 - GET 2020-08-24",
                "10.0.0.2 - GET 2020-08-20"};
        System.out.println(frequentIp(lines));

    }
}
