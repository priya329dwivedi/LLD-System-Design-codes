package org.designpattern.practiceQuestions.ParkingLot.model;

public class Ticket {
    public int ticketId;
    public Vehicle vehicle;
    public ParkingSlot slot;
    public long entryTime;

    public Ticket(int ticketId, Vehicle vehicle, ParkingSlot slot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = System.currentTimeMillis();
    }
}
