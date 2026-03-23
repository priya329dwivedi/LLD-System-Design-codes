/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.observer.practiceObserver.StockPriceAlertSystem;

import org.designpattern.observer.practiceObserver.StockPriceAlertSystem.ConcreteObservers.EmailUser;
import org.designpattern.observer.practiceObserver.StockPriceAlertSystem.ConcreteObservers.PushUser;
import org.designpattern.observer.practiceObserver.StockPriceAlertSystem.ConcreteObservers.SmsUser;

public class Main {
    public static void main(String[] args) {
        // Create stocks (subjects)
        Stock aapl = new Stock("AAPL", 150.0);
        Stock tsla = new Stock("TSLA", 700.0);

        // Create users (observers) with different notification channels
        EmailUser user1 = new EmailUser("Priya", "priya@example.com");
        SmsUser user2 = new SmsUser("Rahul", "+91-9876543210");
        PushUser user3 = new PushUser("Amit", "device-001");

        // Subscribe users to AAPL
        System.out.println("=== Subscribing to AAPL ===");
        aapl.subscribe(user1);
        aapl.subscribe(user2);
        aapl.subscribe(user3);

        // Subscribe some users to TSLA
        System.out.println("\n=== Subscribing to TSLA ===");
        tsla.subscribe(user1);
        tsla.subscribe(user3);

        // Price changes - all subscribers get notified
        aapl.setPrice(155.5);
        tsla.setPrice(720.0);

        // Unsubscribe user2 from AAPL
        System.out.println("\n=== Unsubscribing Rahul from AAPL ===");
        aapl.unsubscribe(user2);

        // Only remaining subscribers get notified
        aapl.setPrice(160.0);
    }
}
