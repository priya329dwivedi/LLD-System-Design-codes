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

public class SeasonalSale implements DiscountStrategy {
    String seasonName;
    int seasonalRate;

    public SeasonalSale(String seasonName, int seasonalRate) {
        this.seasonName = seasonName;
        this.seasonalRate = seasonalRate;
    }

    @Override
    public int calculateFinalPrice(int cartTotal, List<Item> items) {
        System.out.println(seasonName + " Sale applied: " + seasonalRate + "% off");
        return cartTotal - (cartTotal * seasonalRate / 100);
    }
}
