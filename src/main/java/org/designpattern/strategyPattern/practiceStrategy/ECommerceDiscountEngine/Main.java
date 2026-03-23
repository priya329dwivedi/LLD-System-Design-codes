/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine;

import org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine.ConcreteClasses.BOGO;
import org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine.ConcreteClasses.FlatOff;
import org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine.ConcreteClasses.PercentageDiscount;
import org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine.ConcreteClasses.SeasonalSale;

import java.io.OutputStream;

public class Main {
    public static void main(String[] args) {
        Item item1 = new Item("abc",20);
        Item item2 = new  Item("def", 80);
        Item item3 = new Item("ghi",100);
        Item item4 = new Item("xyz",10089);
        BOGO bogo= new BOGO();
        FlatOff flatOff = new FlatOff(200,349);
        ShoppingCart shoppingCart = new ShoppingCart(bogo);
        shoppingCart.addItem(item1);
        shoppingCart.addItem(item2);
        shoppingCart.addItem(item3);
        System.out.println("shopping cart user 1 with BOGO: "+shoppingCart.finalPrice());
        shoppingCart.setDiscountStrategy(flatOff);
        System.out.println("shopping cart user 1 with flatoff: "+shoppingCart.finalPrice());
        PercentageDiscount percentageDiscount = new PercentageDiscount(30);
        ShoppingCart shoppingCart2 = new ShoppingCart(percentageDiscount);
        shoppingCart2.addItem(item3);
        shoppingCart2.addItem(item4);
        System.out.println("Shopping cart 2: with percent discount= "+ shoppingCart2.finalPrice());
        // Switch to Seasonal Sale at runtime
        SeasonalSale seasonalSale = new SeasonalSale("Diwali",25);
        shoppingCart2.setDiscountStrategy(seasonalSale);
        System.out.println("Shopping cart 2: with seasonal sale= "+ shoppingCart2.finalPrice());
    }


//    Output:-
//    shopping cart user 1 with BOGO: 180
//    shopping cart user 1 with flatoff: 200
//    Shopping cart 2: with percent discount= 7133
//    Diwali Sale applied: 25% off
//    Shopping cart 2: with seasonal sale= 7642
}
