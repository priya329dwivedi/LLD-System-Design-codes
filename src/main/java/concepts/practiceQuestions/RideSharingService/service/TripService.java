package concepts.practiceQuestions.RideSharingService.service;

import concepts.practiceQuestions.RideSharingService.model.Trip;
import concepts.practiceQuestions.RideSharingService.model.TripStatus;
import concepts.practiceQuestions.RideSharingService.model.Vehicle;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TripService {
    private final Map<String, Trip>    trips    = new HashMap<>();
    private final Map<String, Vehicle> vehicles = new HashMap<>();
    private int idCounter = 1;

    public void registerVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
    }

    public Trip scheduleTrip(String routeId, String driverId, String vehicleId, LocalDateTime departureTime) {
        Vehicle vehicle = vehicles.get(vehicleId);
        if (vehicle == null) { System.out.println("[TripService] Vehicle not found: " + vehicleId); return null; }

        String id = "T" + idCounter++;
        Trip trip = new Trip(id, routeId, driverId, vehicleId, departureTime, vehicle.getCapacity());
        trips.put(id, trip);
        System.out.println("[TripService] Scheduled trip " + id + " | route=" + routeId
            + " | vehicle=" + vehicle.getType() + " | seats=" + vehicle.getCapacity()
            + " | departs=" + departureTime);
        return trip;
    }

    public void updateStatus(String tripId, TripStatus status) {
        Trip trip = trips.get(tripId);
        if (trip == null) return;
        trip.setStatus(status);
        System.out.println("[TripService] Trip " + tripId + " → " + status);
    }

    public Trip getTrip(String tripId) { return trips.get(tripId); }
}
