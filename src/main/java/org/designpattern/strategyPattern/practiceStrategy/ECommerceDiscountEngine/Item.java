/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.ECommerceDiscountEngine;

import lombok.Getter;

@Getter
public class Item {
    String ItemName;
    int cost;
    public Item(String itemName, int cost){
        this.ItemName= itemName;
        this.cost=cost;
    }

}
