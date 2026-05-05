package concepts.practiceQuestions.RideSharingService.model;

public class Booking {
    private final String  id;
    private final String  tripId;
    private final String  employeeId;
    private final String  pickupStopId;
    private       boolean cancelled;

    public Booking(String id, String tripId, String employeeId, String pickupStopId) {
        this.id           = id;
        this.tripId       = tripId;
        this.employeeId   = employeeId;
        this.pickupStopId = pickupStopId;
        this.cancelled    = false;
    }

    public void cancel() { this.cancelled = true; }

    public String  getId()          { return id; }
    public String  getTripId()      { return tripId; }
    public String  getEmployeeId()  { return employeeId; }
    public String  getPickupStopId(){ return pickupStopId; }
    public boolean isCancelled()    { return cancelled; }
}
