/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.DSA.question3;
public class Main {
    public static void main(String[] args) {

        int[] piles= {805306368,805306368,805306368};
        int ans= minEatingSpeed(piles,1000000000);
        System.out.println(ans);

    }

    public static long calculateHours(int[] piles,int mid){
        long ans=0;
        for(int i=0;i<piles.length;i++){
            ans+= (piles[i]+mid-1)/mid;
        }
        return ans;
    }

    private static int minEatingSpeed(int[] piles, int h) {
        int n= piles.length;
        int low =1;
        int high= Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            high= Math.max(high,piles[i]);
        }
        while(low<=high){
            int mid= low+(high-low)/2;
            long hours= calculateHours(piles,mid);
            if(hours<=h){
                high= mid-1;
            }
            else{
                low=mid+1;
            }
        }
        return low;
    }
}
