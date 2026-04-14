package org.designpattern.practiceQuestions.ParkingLot;

import org.designpattern.practiceQuestions.ParkingLot.model.ParkingSlot;
import org.designpattern.practiceQuestions.ParkingLot.model.SlotSize;
import org.designpattern.practiceQuestions.ParkingLot.model.Ticket;
import org.designpattern.practiceQuestions.ParkingLot.model.Vehicle;
import org.designpattern.practiceQuestions.ParkingLot.model.VehicleType;
import org.designpattern.practiceQuestions.ParkingLot.store.ParkingStore;

import java.util.HashMap;
import java.util.Map;

public class ParkingService {
    ParkingStore store;

    public ParkingService(ParkingStore store) {
        this.store = store;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        SlotSize size = getSlotSize(vehicle.type);
        ParkingSlot slot = store.findAvailableSlot(size);
        slot.isOccupied = true;
        Ticket ticket = new Ticket(store.nextTicketId(), vehicle, slot);
        store.saveTicket(ticket);
        System.out.println("Parked :  " + vehicle.licensePlate + " at slot " + slot.slotId
                + "  [Ticket #" + ticket.ticketId + "]");
        return ticket;
    }

    public int exitVehicle(String licensePlate) {
        Ticket ticket = store.getTicket(licensePlate);
        ticket.slot.isOccupied = false;
        store.removeTicket(licensePlate);
        int fee = calculateFee(ticket);
        System.out.println("Exit   : " + licensePlate + " | Duration: " + getDurationHours(ticket)
                + " hr(s) | Fee: Rs " + fee);
        return fee;
    }

    public void displayAvailability() {
        Map<SlotSize, Integer> available = new HashMap<>();
        for (ParkingSlot slot : store.slots) {
            if (!slot.isOccupied) {
                available.put(slot.size, available.getOrDefault(slot.size, 0) + 1);
            }
        }
        System.out.println("\n--- Parking Availability ---");
        System.out.println("  SMALL  (Bikes) : " + available.getOrDefault(SlotSize.SMALL, 0));
        System.out.println("  MEDIUM (Cars)  : " + available.getOrDefault(SlotSize.MEDIUM, 0));
        System.out.println("  LARGE  (Trucks): " + available.getOrDefault(SlotSize.LARGE, 0));
    }

    private SlotSize getSlotSize(VehicleType type) {
        if (type == VehicleType.BIKE) return SlotSize.SMALL;
        if (type == VehicleType.CAR)  return SlotSize.MEDIUM;
        return SlotSize.LARGE;
    }

    private int calculateFee(Ticket ticket) {
        int hours = getDurationHours(ticket);
        return hours * getRatePerHour(ticket.vehicle.type);
    }

    private int getDurationHours(Ticket ticket) {
        long durationMs = System.currentTimeMillis() - ticket.entryTime;
        int hours = (int) (durationMs / 3600000);
        return hours < 1 ? 1 : hours;  // minimum 1 hour
    }

    private int getRatePerHour(VehicleType type) {
        if (type == VehicleType.BIKE) return 20;
        if (type == VehicleType.CAR)  return 50;
        return 100;  // TRUCK
    }
}
