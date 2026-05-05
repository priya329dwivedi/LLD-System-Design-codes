package concepts.practiceQuestions.RideSharingService.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// A scheduled instance of a Route — assigns a driver + vehicle to a route at a fixed time.
public class Trip {
    private final String        id;
    private final String        routeId;
    private final String        driverId;
    private final String        vehicleId;
    private final LocalDateTime departureTime;
    private       TripStatus    status;
    private       int           availableSeats;
    private final List<Booking> bookings;

    public Trip(String id, String routeId, String driverId, String vehicleId,
                LocalDateTime departureTime, int capacity) {
        this.id             = id;
        this.routeId        = routeId;
        this.driverId       = driverId;
        this.vehicleId      = vehicleId;
        this.departureTime  = departureTime;
        this.status         = TripStatus.SCHEDULED;
        this.availableSeats = capacity;
        this.bookings       = new ArrayList<>();
    }

    public boolean reserveSeat() {
        if (availableSeats <= 0 || status != TripStatus.SCHEDULED) return false;
        availableSeats--;
        return true;
    }

    public void releaseSeat() { availableSeats++; }

    public void addBooking(Booking b)    { bookings.add(b); }
    public void setStatus(TripStatus s)  { this.status = s; }

    public String        getId()            { return id; }
    public String        getRouteId()       { return routeId; }
    public String        getDriverId()      { return driverId; }
    public String        getVehicleId()     { return vehicleId; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public TripStatus    getStatus()        { return status; }
    public int           getAvailableSeats(){ return availableSeats; }
    public List<Booking> getBookings()      { return bookings; }
}
