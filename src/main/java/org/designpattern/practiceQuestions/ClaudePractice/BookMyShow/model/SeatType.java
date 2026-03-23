package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model;

public enum SeatType {
    REGULAR(150),
    PREMIUM(300),
    RECLINER(500);

    private final double basePrice;

    SeatType(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
