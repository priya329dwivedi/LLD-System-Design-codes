package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.observer;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Booking;

public class SmsNotifier implements BookingObserver {
    @Override
    public void onBookingConfirmed(Booking booking) {
        System.out.println("[SMS -> " + booking.getUser().getPhone() + "] Booking confirmed for " + booking.getShow().getMovie().getName());
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        System.out.println("[SMS -> " + booking.getUser().getPhone() + "] Booking cancelled for " + booking.getShow().getMovie().getName());
    }

    @Override
    public void onPaymentFailed(Booking booking) {
        System.out.println("[SMS -> " + booking.getUser().getPhone() + "] Payment failed for " + booking.getShow().getMovie().getName());
    }
}
