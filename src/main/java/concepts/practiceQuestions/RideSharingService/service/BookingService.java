package concepts.practiceQuestions.RideSharingService.service;

import concepts.practiceQuestions.RideSharingService.model.Booking;
import concepts.practiceQuestions.RideSharingService.model.Trip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService {
    private final TripService          tripService;
    private final Map<String, Booking> bookings  = new HashMap<>();
    private int idCounter = 1;

    public BookingService(TripService tripService) {
        this.tripService = tripService;
    }

    // synchronized: seat reservation is a critical section — two employees must not claim the last seat simultaneously
    public synchronized Optional<Booking> bookSeat(String tripId, String employeeId, String pickupStopId) {
        Trip trip = tripService.getTrip(tripId);
        if (trip == null) { System.out.println("[BookingService] Trip not found: " + tripId); return Optional.empty(); }

        if (!trip.reserveSeat()) {
            System.out.println("[BookingService] No seats available on trip " + tripId + " for " + employeeId);
            return Optional.empty();
        }

        String  id      = "B" + idCounter++;
        Booking booking = new Booking(id, tripId, employeeId, pickupStopId);
        bookings.put(id, booking);
        trip.addBooking(booking);
        System.out.println("[BookingService] Booked " + id + " | employee=" + employeeId
            + " | trip=" + tripId + " | pickup=" + pickupStopId
            + " | seats left=" + trip.getAvailableSeats());
        return Optional.of(booking);
    }

    public synchronized void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null || booking.isCancelled()) return;

        booking.cancel();
        Trip trip = tripService.getTrip(booking.getTripId());
        if (trip != null) trip.releaseSeat();
        System.out.println("[BookingService] Cancelled " + bookingId
            + " | seats now=" + (trip != null ? trip.getAvailableSeats() : "?"));
    }

    public List<Booking> getActiveBookings(String tripId) {
        Trip trip = tripService.getTrip(tripId);
        if (trip == null) return List.of();
        return trip.getBookings().stream()
            .filter(b -> !b.isCancelled())
            .collect(Collectors.toList());
    }
}
