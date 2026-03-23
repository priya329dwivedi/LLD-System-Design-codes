package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.observer;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Booking;

public class EmailNotifier implements BookingObserver {
    @Override
    public void onBookingConfirmed(Booking booking) {
        System.out.println("[Email -> " + booking.getUser().getEmail() + "] Booking confirmed! " + booking);
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        System.out.println("[Email -> " + booking.getUser().getEmail() + "] Booking cancelled. " + booking);
    }

    @Override
    public void onPaymentFailed(Booking booking) {
        System.out.println("[Email -> " + booking.getUser().getEmail() + "] Payment failed! Seats released. " + booking);
    }
}
