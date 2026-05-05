package concepts.practiceQuestions.ParkingLot;

import concepts.practiceQuestions.ParkingLot.model.*;

import java.util.*;

public class ParkingLot {
    private final String        name;
    private final List<Floor>   floors;
    private final Map<String, Ticket> activeTickets; // ticketId → Ticket
    private int ticketCounter = 1;

    public ParkingLot(String name) {
        this.name          = name;
        this.floors        = new ArrayList<>();
        this.activeTickets = new HashMap<>();
    }

    public void addFloor(Floor floor) { floors.add(floor); }

    // Scans floors top-to-bottom, returns first available slot matching vehicle type.
    // Returns Optional.empty() if lot is full for that vehicle type.
    public Optional<Ticket> park(Vehicle vehicle) {
        for (Floor floor : floors) {
            Slot slot = floor.findAvailableSlot(vehicle.getType());
            if (slot != null) {
                slot.occupy();
                Ticket ticket = new Ticket("TKT-" + ticketCounter++, vehicle, slot, floor.getNumber());
                activeTickets.put(ticket.getId(), ticket);
                System.out.println("[Park]   " + vehicle + " → " + ticket);
                return Optional.of(ticket);
            }
        }
        System.out.println("[Park]   FULL — no slot available for " + vehicle);
        return Optional.empty();
    }

    public void unpark(String ticketId) {
        Ticket ticket = activeTickets.remove(ticketId);
        if (ticket == null) { System.out.println("[Unpark] Ticket not found: " + ticketId); return; }
        ticket.markExit();
        ticket.getSlot().free();
        System.out.println("[Unpark] " + ticket.getVehicle() + " left | fee=₹" + ticket.calculateFee()
            + " | slot " + ticket.getSlot().getId() + " is now free");
    }

    public void printAvailability() {
        System.out.println("\n[" + name + "] Availability:");
        for (Floor floor : floors) {
            for (VehicleType type : VehicleType.values()) {
                int count = floor.availableCount(type);
                if (count > 0)
                    System.out.printf("  Floor %d | %-5s → %d slots free%n", floor.getNumber(), type, count);
            }
        }
    }
}
