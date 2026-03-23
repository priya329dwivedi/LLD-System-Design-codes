package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.strategy;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.SeatType;

public interface PricingStrategy {
    double calculatePrice(SeatType seatType);
}
