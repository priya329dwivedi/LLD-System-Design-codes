/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.observer.practiceObserver.StockPriceAlertSystem.ConcreteObservers;

import org.designpattern.observer.practiceObserver.StockPriceAlertSystem.StockObserver;

public class EmailUser implements StockObserver {
    private String userName;
    private String email;

    public EmailUser(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("[Email] " + userName + " (" + email + "): " + stockSymbol + " is now $" + price);
    }
}
