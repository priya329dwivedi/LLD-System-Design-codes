package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.strategy;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.SeatType;

public class BasePricing implements PricingStrategy {
    @Override
    public double calculatePrice(SeatType seatType) {
        return seatType.getBasePrice();
    }
}
