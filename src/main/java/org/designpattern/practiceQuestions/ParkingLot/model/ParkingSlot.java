package org.designpattern.practiceQuestions.ParkingLot.model;

public class ParkingSlot {
    public String slotId;
    public SlotSize size;
    public boolean isOccupied;

    public ParkingSlot(String slotId, SlotSize size) {
        this.slotId = slotId;
        this.size = size;
        this.isOccupied = false;
    }
}
