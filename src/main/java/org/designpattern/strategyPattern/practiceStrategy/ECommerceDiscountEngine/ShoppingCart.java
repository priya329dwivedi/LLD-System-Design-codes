/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Item> items;
    @Setter
    DiscountStrategy discountStrategy;
    public ShoppingCart(DiscountStrategy discountStrategy){
        this.items= new ArrayList<>();
        this.discountStrategy= discountStrategy;
    }
    public void addItem(Item item){
        items.add(item);
    }
    private int calculateCartValue(){
        int size= this.items.size();
        int cartValue=0;
        for(int i=0;i<size;i++){
            Item item= items.get(i);
            cartValue += item.getCost();
        }
        return cartValue;
    }
    public int finalPrice(){

        return discountStrategy.calculateFinalPrice(calculateCartValue(),items);
    }
}
