package concepts.selfPractice.MultiLevelParkingLot.Model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final Slot slot;
    private final Integer floorNumber;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Ticket(String ticketId, Vehicle vehicle,Slot slot,int floorNumber) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot=slot;
        this.floorNumber=floorNumber;
        this.entryTime = LocalDateTime.now();
    }

    public void markExit(){
        this.exitTime= LocalDateTime.now();
    }

    public int calculateFees(){
        LocalDateTime end;
        end = this.exitTime==null? LocalDateTime.now(): exitTime;
        return 20* Math.min(1,end.getHour()-entryTime.getHour());
    }

}
