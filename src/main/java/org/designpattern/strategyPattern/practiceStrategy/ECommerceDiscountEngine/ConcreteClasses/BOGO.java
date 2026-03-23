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

import java.util.Comparator;
import java.util.List;

public class BOGO implements DiscountStrategy {

    @Override
    public int calculateFinalPrice(int cartTotal, List<Item> items) {
        items.sort(Comparator.comparingDouble(Item::getCost));
        return cartTotal-items.get(0).getCost();
    }
}
