package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow;

import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Booking;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Movie;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Screen;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Seat;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.SeatType;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Show;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.ShowSeat;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.Theatre;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model.User;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.observer.EmailNotifier;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.observer.SmsNotifier;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment.PaymentFactory;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment.PaymentMethod;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.service.BookingService;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.strategy.BasePricing;
import org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.strategy.WeekendSurgePricing;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ========== Setup Theatre & Screen ==========
        Screen screen1 = new Screen("SCR1", "Screen 1");
        // Row A - Regular (6 seats)
        for (int i = 1; i <= 6; i++) {
            screen1.addSeat(new Seat("A" + i, "A", i, SeatType.REGULAR));
        }
        // Row B - Regular
        for (int i = 1; i <= 6; i++) {
            screen1.addSeat(new Seat("B" + i, "B", i, SeatType.REGULAR));
        }
        // Row C - Premium
        for (int i = 1; i <= 6; i++) {
            screen1.addSeat(new Seat("C" + i, "C", i, SeatType.PREMIUM));
        }
        // Row D - Recliner
        for (int i = 1; i <= 4; i++) {
            screen1.addSeat(new Seat("D" + i, "D", i, SeatType.RECLINER));
        }

        Theatre theatre = new Theatre("TH1", "PVR Phoenix", "Mumbai");
        theatre.addScreen(screen1);

        Movie movie = new Movie("M1", "Pushpa 2", "Action", 180);

        Show show = new Show("SH1", movie, screen1, "7:00 PM", "2026-03-23");

        // ========== Users ==========
        User priya = new User("U1", "Priya", "priya@email.com", "7905398325");
        User haotami = new User("U2", "Haotami", "haotami@email.com", "8905398325");

        // ========== Booking Service (Singleton) ==========
        BookingService bookingService = BookingService.getInstance(new BasePricing());
        bookingService.addObserver(new EmailNotifier());
        bookingService.addObserver(new SmsNotifier());

        // ========== Show initial seat layout ==========
        System.out.println("========== Initial Seat Layout ==========");
        show.displaySeatLayout();

        // ========== Priya books 2 Premium seats ==========
        System.out.println("\n========== Priya Booking (Base Pricing) ==========\n");

        List<ShowSeat> priyaSeats = bookingService.selectSeats(show, Arrays.asList("C2", "C3"), priya);
        show.displaySeatLayout();  // C2, C3 should show [LK]

        PaymentMethod upi = PaymentFactory.createPayment("UPI");
        Booking priyaBooking = bookingService.confirmBooking(priya, show, priyaSeats, upi);
        System.out.println("\n" + priyaBooking);

        // ========== Show layout after Priya's booking ==========
        System.out.println("\n========== After Priya's Booking ==========");
        show.displaySeatLayout();  // C2, C3 should show [XX]

        // ========== Haotami tries to book same seats (should fail) ==========
        System.out.println("\n========== Haotami tries C2, C3 (already booked) ==========\n");
        List<ShowSeat> haotamiSeats = bookingService.selectSeats(show, Arrays.asList("C2", "C3"), haotami);

        // ========== Haotami books Recliner with Weekend Surge ==========
        System.out.println("\n========== Haotami Booking (Weekend Surge 1.5x) ==========\n");

        bookingService.setPricingStrategy(new WeekendSurgePricing(1.5));

        haotamiSeats = bookingService.selectSeats(show, Arrays.asList("D1", "D2"), haotami);
        double total = bookingService.calculateTotal(haotamiSeats);
        System.out.println("Total for 2 Recliners (1.5x surge): Rs " + total);

        PaymentMethod card = PaymentFactory.createPayment("CreditCard");
        Booking haotamiBooking = bookingService.confirmBooking(haotami, show, haotamiSeats, card);
        System.out.println("\n" + haotamiBooking);

        // ========== Payment failure scenario ==========
        System.out.println("\n========== Payment Failure (Wallet with low balance) ==========\n");

        bookingService.setPricingStrategy(new BasePricing());
        List<ShowSeat> failSeats = bookingService.selectSeats(show, Arrays.asList("A1", "A2", "A3"), priya);
        PaymentMethod wallet = PaymentFactory.createPayment("Wallet");  // Rs 500 balance
        Booking failedBooking = bookingService.confirmBooking(priya, show, failSeats, wallet);
        System.out.println("\n" + failedBooking);

        // ========== Seats released after failure — show layout ==========
        System.out.println("\n========== Final Seat Layout ==========");
        show.displaySeatLayout();  // A1-A3 should be available again

        // ========== Available seats count ==========
        System.out.println("Available seats: " + show.getAvailableSeats().size());

        // ========== Booking history ==========
        System.out.println("\n========== Priya's Booking History ==========");
        for (Booking b : bookingService.getBookingHistory(priya.getId())) {
            System.out.println("  " + b);
        }
    }
}
