package org.designpattern.practiceQuestions.ParkingLot.store;

import org.designpattern.practiceQuestions.ParkingLot.model.ParkingSlot;
import org.designpattern.practiceQuestions.ParkingLot.model.SlotSize;
import org.designpattern.practiceQuestions.ParkingLot.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingStore {
    public List<ParkingSlot> slots = new ArrayList<>();
    Map<String, Ticket> activeTickets = new HashMap<>();  // licensePlate -> Ticket
    int ticketCounter = 1;

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    // Returns first available slot of the given size (happy flow: slot always exists)
    public ParkingSlot findAvailableSlot(SlotSize size) {
        for (ParkingSlot slot : slots) {
            if (slot.size == size && !slot.isOccupied) return slot;
        }
        return null;
    }

    public void saveTicket(Ticket ticket) {
        activeTickets.put(ticket.vehicle.licensePlate, ticket);
    }

    public Ticket getTicket(String licensePlate) {
        return activeTickets.get(licensePlate);
    }

    public void removeTicket(String licensePlate) {
        activeTickets.remove(licensePlate);
    }

    public int nextTicketId() {
        return ticketCounter++;
    }
}
