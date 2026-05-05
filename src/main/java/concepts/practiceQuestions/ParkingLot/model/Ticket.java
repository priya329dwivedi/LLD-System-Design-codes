package concepts.practiceQuestions.ParkingLot.model;

import java.time.LocalDateTime;
import java.time.Duration;

public class Ticket {
    private final String        id;
    private final Vehicle       vehicle;
    private final Slot          slot;
    private final int           floorNumber;
    private final LocalDateTime entryTime;
    private       LocalDateTime exitTime;

    public Ticket(String id, Vehicle vehicle, Slot slot, int floorNumber) {
        this.id          = id;
        this.vehicle     = vehicle;
        this.slot        = slot;
        this.floorNumber = floorNumber;
        this.entryTime   = LocalDateTime.now();
    }

    public void markExit() { this.exitTime = LocalDateTime.now(); }

    // Flat rate: ₹20/hour, minimum 1 hour
    public long calculateFee() {
        LocalDateTime end   = exitTime != null ? exitTime : LocalDateTime.now();
        long          hours = Math.max(1, Duration.between(entryTime, end).toHours());
        return hours * 20;
    }

    public String        getId()         { return id; }
    public Vehicle       getVehicle()    { return vehicle; }
    public Slot          getSlot()       { return slot; }
    public int           getFloorNumber(){ return floorNumber; }
    public LocalDateTime getEntryTime()  { return entryTime; }

    @Override
    public String toString() {
        return "Ticket{" + id + " | " + vehicle + " | floor=" + floorNumber + " | slot=" + slot.getId() + "}";
    }
}
