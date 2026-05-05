package concepts.practiceQuestions.RideSharingService;

import concepts.practiceQuestions.RideSharingService.model.*;
import concepts.practiceQuestions.RideSharingService.service.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        RouteService   routeService   = new RouteService();
        TripService    tripService    = new TripService();
        BookingService bookingService = new BookingService(tripService);

        // ── Setup: vehicles ───────────────────────────────────────────────────
        Vehicle suv     = new Vehicle("V1", "SUV", 4);
        Vehicle minibus = new Vehicle("V2", "Minibus", 3);
        tripService.registerVehicle(suv);
        tripService.registerVehicle(minibus);

        Driver driver1 = new Driver("D1", "Ramesh");
        Driver driver2 = new Driver("D2", "Suresh");

        // ── Flow 1: Create route → schedule trip → employees book seats ────────
        separator("FLOW 1: Route setup + seat booking");

        Route route = routeService.createRoute("Koramangala → Whitefield Office");
        routeService.addStop(route.getId(), "S1", "Koramangala 4th Block", 1);
        routeService.addStop(route.getId(), "S2", "Marathahalli Bridge",   2);
        routeService.addStop(route.getId(), "S3", "Whitefield Office",     3);

        Trip trip = tripService.scheduleTrip(route.getId(), driver1.getId(), "V2",
            LocalDateTime.of(2025, 5, 1, 9, 0));

        Employee e1 = new Employee("E1", "Alice");
        Employee e2 = new Employee("E2", "Bob");
        Employee e3 = new Employee("E3", "Carol");

        Optional<Booking> b1 = bookingService.bookSeat(trip.getId(), e1.getId(), "S1");
        Optional<Booking> b2 = bookingService.bookSeat(trip.getId(), e2.getId(), "S2");
        Optional<Booking> b3 = bookingService.bookSeat(trip.getId(), e3.getId(), "S1");

        // ── Flow 2: Overbooking rejected ──────────────────────────────────────
        separator("FLOW 2: Overbooking beyond vehicle capacity");

        Employee e4 = new Employee("E4", "Dave");
        bookingService.bookSeat(trip.getId(), e4.getId(), "S2"); // minibus has 3 seats — should fail

        // ── Flow 3: Cancel + re-book freed seat ───────────────────────────────
        separator("FLOW 3: Cancellation frees a seat");

        b2.ifPresent(b -> bookingService.cancelBooking(b.getId()));
        bookingService.bookSeat(trip.getId(), e4.getId(), "S2"); // now succeeds

        // ── Summary ───────────────────────────────────────────────────────────
        separator("TRIP SUMMARY");
        System.out.println("Trip: " + trip.getId() + " | status=" + trip.getStatus()
            + " | seats left=" + trip.getAvailableSeats());
        System.out.println("Active bookings:");
        bookingService.getActiveBookings(trip.getId())
            .forEach(b -> System.out.println("  " + b.getId()
                + " | employee=" + b.getEmployeeId()
                + " | pickup="  + b.getPickupStopId()));

        tripService.updateStatus(trip.getId(), TripStatus.IN_PROGRESS);
        tripService.updateStatus(trip.getId(), TripStatus.COMPLETED);
    }

    private static void separator(String label) {
        System.out.println("\n── " + label + " " + "─".repeat(Math.max(0, 50 - label.length())));
    }
}
