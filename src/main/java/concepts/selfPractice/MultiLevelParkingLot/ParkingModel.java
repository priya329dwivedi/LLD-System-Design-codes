package concepts.selfPractice.MultiLevelParkingLot;

import concepts.selfPractice.MultiLevelParkingLot.Factory.FloorFactory;
import concepts.selfPractice.MultiLevelParkingLot.Model.*;
import concepts.selfPractice.MultiLevelParkingLot.Strategy.FloorStrategy;

import java.util.ArrayList;
import java.util.List;

public class ParkingModel {
    List<Floor> floors;
    private final String name;
    List<Ticket> activeTickets;
    private int ticketCounter;

    public ParkingModel(String name) {
        this.name= name;
        this.floors = new ArrayList<>();
        this.activeTickets = new ArrayList<>();
    }

    public void addFloor(int floorNumber, int bike, int car, int truck, FloorStrategy strategy){
        floors.add(FloorFactory.createFloor(floorNumber,bike,car,truck,strategy));
    }

    public void park(Vehicle vehicle){
        Slot slot=null;
        int floorNumber=-1;
        VehicleType type= vehicle.getType();
        for(Floor floor: floors){
            slot= floor.findSlot(type);
            if(slot!=null){
                floorNumber= floor.getFloorNumber();
                break;
            }
        }
        if(slot==null) {
            System.out.printf("No Available slots of VehicleType: [%s] for VehicleId: [%s] %n", type,vehicle.getVehicleId());
            return;
        }

        Ticket ticket = new Ticket("T-"+type+ticketCounter,vehicle,slot,floorNumber);
        ticketCounter++;
        slot.occupy();
        activeTickets.add(ticket);
    }

    public void unPark(String ticketId){
        for(int i=0;i<activeTickets.size();i++){
            Ticket ticket = activeTickets.get(i);
            if(ticket.getTicketId().equals(ticketId)){
                ticket.markExit();
                ticket.getSlot().free();
                ticket.calculateFees();
                activeTickets.remove(ticket);
                break;
            }
        }
    }

    public void printAvailableSlots(){
        for(Floor floor: floors){
            System.out.printf("Available seats on Floor %d %n",floor.getFloorNumber());
            floor.getAvailableSlots();
        }
    }

    public void getAvailableTickets(){
        System.out.println("----Available Tickets----");
        for(Ticket ticket: activeTickets){
            System.out.println(ticket.getTicketId());
        }
    }
}
