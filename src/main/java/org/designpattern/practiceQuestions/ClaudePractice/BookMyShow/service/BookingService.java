package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.service;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Booking;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.BookingStatus;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Show;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.ShowSeat;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.User;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.observer.BookingObserver;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment.PaymentMethod;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.strategy.PricingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingService {
    private static BookingService instance;
    private final List<BookingObserver> observers;
    private final List<Booking> bookingHistory;
    private PricingStrategy pricingStrategy;

    private BookingService(PricingStrategy pricingStrategy) {
        this.observers = new ArrayList<>();
        this.bookingHistory = new ArrayList<>();
        this.pricingStrategy = pricingStrategy;
    }

    public static BookingService getInstance(PricingStrategy pricingStrategy) {
        if (instance == null) {
            instance = new BookingService(pricingStrategy);
        }
        return instance;
    }

    public void addObserver(BookingObserver observer) {
        observers.add(observer);
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    // Step 1: Lock seats for user
    public List<ShowSeat> selectSeats(Show show, List<String> seatIds, User user) {
        List<ShowSeat> lockedSeats = new ArrayList<>();
        for (String seatId : seatIds) {
            ShowSeat showSeat = show.getShowSeat(seatId);
            if (showSeat == null) {
                System.out.println("Seat " + seatId + " does not exist");
                releaseSeats(lockedSeats);
                return null;
            }
            if (!showSeat.lock(user.getId())) {
                System.out.println("Seat " + showSeat.getSeat().getLabel() + " is not available (status: " + showSeat.getStatus() + ")");
                releaseSeats(lockedSeats);
                return null;
            }
            lockedSeats.add(showSeat);
        }
        System.out.println(lockedSeats.size() + " seat(s) locked for " + user.getName());
        return lockedSeats;
    }

    // Step 2: Calculate total price
    public double calculateTotal(List<ShowSeat> seats) {
        double total = 0;
        for (ShowSeat showSeat : seats) {
            total += pricingStrategy.calculatePrice(showSeat.getSeat().getSeatType());
        }
        return total;
    }

    // Step 3: Confirm booking after payment
    public Booking confirmBooking(User user, Show show, List<ShowSeat> lockedSeats, PaymentMethod paymentMethod) {
        double total = calculateTotal(lockedSeats);
        String bookingId = UUID.randomUUID().toString().substring(0, 8);
        Booking booking = new Booking(bookingId, user, show, lockedSeats, total);

        boolean paymentSuccess = paymentMethod.pay(total);

        if (!paymentSuccess) {
            booking.setStatus(BookingStatus.CANCELLED);
            releaseSeats(lockedSeats);
            notifyPaymentFailed(booking);
            return booking;
        }

        for (ShowSeat seat : lockedSeats) {
            seat.confirmBooking(user.getId());
        }
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingHistory.add(booking);
        notifyBookingConfirmed(booking);
        return booking;
    }

    // Cancel a booking and release seats
    public void cancelBooking(Booking booking) {
        booking.setStatus(BookingStatus.CANCELLED);
        releaseSeats(booking.getSeats());
        notifyBookingCancelled(booking);
    }

    public List<Booking> getBookingHistory(String userId) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookingHistory) {
            if (booking.getUser().getId().equals(userId)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    private void releaseSeats(List<ShowSeat> seats) {
        for (ShowSeat seat : seats) {
            seat.release();
        }
    }

    private void notifyBookingConfirmed(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.onBookingConfirmed(booking);
        }
    }

    private void notifyBookingCancelled(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.onBookingCancelled(booking);
        }
    }

    private void notifyPaymentFailed(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.onPaymentFailed(booking);
        }
    }
}
