package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.observer;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Booking;

public interface BookingObserver {
    void onBookingConfirmed(Booking booking);
    void onBookingCancelled(Booking booking);
    void onPaymentFailed(Booking booking);
}
