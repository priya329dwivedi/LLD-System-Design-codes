package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.strategy;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.SeatType;

public class WeekendSurgePricing implements PricingStrategy {
    private final double surgeMultiplier;

    public WeekendSurgePricing(double surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public double calculatePrice(SeatType seatType) {
        return seatType.getBasePrice() * surgeMultiplier;
    }
}
