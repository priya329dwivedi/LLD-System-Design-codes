package concepts.practiceQuestions.ParkingLot.model;

import concepts.practiceQuestions.ParkingLot.strategy.SlotAssignmentStrategy;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private final int                    number;
    private final List<Slot>             slots;
    private final SlotAssignmentStrategy strategy;

    public Floor(int number, SlotAssignmentStrategy strategy) {
        this.number   = number;
        this.slots    = new ArrayList<>();
        this.strategy = strategy;
    }

    public void addSlot(Slot slot) { slots.add(slot); }

    public Slot findAvailableSlot(VehicleType type) {
        return strategy.findSlot(slots, type);
    }

    public int availableCount(VehicleType type) {
        int count = 0;
        for (Slot slot : slots) {
            if (slot.getType() == type && slot.isAvailable()) count++;
        }
        return count;
    }

    public int getNumber() { return number; }
}
