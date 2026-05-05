package concepts.selfPractice.MultiLevelParkingLot;
import concepts.selfPractice.MultiLevelParkingLot.Model.Vehicle;
import concepts.selfPractice.MultiLevelParkingLot.Model.VehicleType;
import concepts.selfPractice.MultiLevelParkingLot.Strategy.FindFirst;

public class Main {
    public static void main(String[] args) {
        ParkingModel model = new ParkingModel("Celesty Parking Lot");
        model.addFloor(2,1,1,1,new FindFirst());
        model.addFloor(3,2,2,0,new FindFirst());
        model.addFloor(1,3,2,1,new FindFirst());
        for(int i=0;i<7;i++){
            Vehicle obj= new Vehicle("KA12-0"+i,VehicleType.BIKE);
            model.park(obj);
        }

//        model.getAvailableTickets();
        model.unPark("T-BIKE4");
        Vehicle bike6= new Vehicle("KA12-06",VehicleType.BIKE);
        model.park(bike6);

        model.unPark("T-BIKE1");
        model.unPark("T-BIKE2");
        model.unPark("T-BIKE3");
//        model.printAvailableSlots();

        model.park(bike6);
        model.park(bike6);
        model.printAvailableSlots();
//        model.getAvailableTickets();
    }
}
