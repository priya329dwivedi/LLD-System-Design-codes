package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.strategy;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.SeatType;

public class CouponDiscount implements PricingStrategy {
    private final int discountPercent;

    public CouponDiscount(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public double calculatePrice(SeatType seatType) {
        return seatType.getBasePrice() * (100 - discountPercent) / 100;
    }
}
