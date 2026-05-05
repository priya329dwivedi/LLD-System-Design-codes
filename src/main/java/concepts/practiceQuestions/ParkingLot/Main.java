package concepts.practiceQuestions.ParkingLot;

import concepts.practiceQuestions.ParkingLot.factory.FloorFactory;
import concepts.practiceQuestions.ParkingLot.model.*;
import concepts.practiceQuestions.ParkingLot.strategy.FirstFitStrategy;
import concepts.practiceQuestions.ParkingLot.strategy.SlotAssignmentStrategy;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        // ── Setup ─────────────────────────────────────────────────────────────
        SlotAssignmentStrategy strategy = new FirstFitStrategy();

        ParkingLot lot = new ParkingLot("Central Plaza Parking");
        lot.addFloor(FloorFactory.create(1, 2, 2, 0, strategy)); // 2 bike, 2 car
        lot.addFloor(FloorFactory.create(2, 0, 1, 1, strategy)); // 1 car, 1 truck
        lot.addFloor(FloorFactory.create(3, 0, 2, 0, strategy)); // 2 car (overflow floor)

        lot.printAvailability();

        // ── Flow 1: Normal parking across vehicle types ───────────────────────
        separator("FLOW 1: Park car, bike, truck");

        Optional<Ticket> t1 = lot.park(new Vehicle("KA-01-1234", VehicleType.CAR));
        Optional<Ticket> t2 = lot.park(new Vehicle("KA-02-5678", VehicleType.BIKE));
        Optional<Ticket> t3 = lot.park(new Vehicle("KA-03-9999", VehicleType.TRUCK));

        lot.printAvailability();

        // ── Flow 2: Multi-level spill — cars fill floor 1, overflow to floor 2 then 3
        separator("FLOW 2: Multi-level overflow");

        lot.park(new Vehicle("KA-04-0001", VehicleType.CAR)); // Floor 1 last car slot
        lot.park(new Vehicle("KA-05-0002", VehicleType.CAR)); // Floor 2 car slot
        lot.park(new Vehicle("KA-06-0003", VehicleType.CAR)); // Floor 3 car slot
        lot.park(new Vehicle("KA-07-0004", VehicleType.CAR)); // Floor 3 last car slot
        lot.park(new Vehicle("KA-08-0005", VehicleType.CAR)); // FULL — all car slots gone

        // ── Flow 3: Unpark frees slot, re-park succeeds ───────────────────────
        separator("FLOW 3: Unpark → slot freed → re-park");

        t1.ifPresent(t -> lot.unpark(t.getId()));
        lot.park(new Vehicle("KA-09-0006", VehicleType.CAR)); // reclaims freed slot

        lot.printAvailability();
    }

    private static void separator(String label) {
        System.out.println("\n── " + label + " " + "─".repeat(Math.max(0, 50 - label.length())));
    }
}
