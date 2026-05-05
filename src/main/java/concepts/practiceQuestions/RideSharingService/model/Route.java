package concepts.practiceQuestions.RideSharingService.model;

import java.util.ArrayList;
import java.util.List;

// A named path made up of ordered stops, ending at a common destination.
public class Route {
    private final String          id;
    private final String          name;
    private final List<RouteStop> stops;

    public Route(String id, String name) {
        this.id    = id;
        this.name  = name;
        this.stops = new ArrayList<>();
    }

    public void addStop(RouteStop stop) { stops.add(stop); }

    public String          getId()    { return id; }
    public String          getName()  { return name; }
    public List<RouteStop> getStops() { return stops; }
}
