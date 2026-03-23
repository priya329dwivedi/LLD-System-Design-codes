/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine.ConcreteClasses;

import org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine.DiscountStrategy;
import org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine.Item;

import java.util.List;

public class FlatOff implements DiscountStrategy {
    int flatOffAmount;
    int cutOffAmount;
    public FlatOff(int flatOffAmount,int cutOffAmount){
        this.flatOffAmount=flatOffAmount;
        this.cutOffAmount=cutOffAmount;
    }
    @Override
    public int calculateFinalPrice(int cartTotal, List<Item> items) {
        if(cartTotal>cutOffAmount){
            return cartTotal-flatOffAmount;
        }
        return cartTotal;
    }
}
