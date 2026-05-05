package concepts.practiceQuestions.RideSharingService.service;

import concepts.practiceQuestions.RideSharingService.model.Route;
import concepts.practiceQuestions.RideSharingService.model.RouteStop;

import java.util.HashMap;
import java.util.Map;

public class RouteService {
    private final Map<String, Route> routes = new HashMap<>();
    private int idCounter = 1;

    public Route createRoute(String name) {
        String id = "R" + idCounter++;
        Route route = new Route(id, name);
        routes.put(id, route);
        System.out.println("[RouteService] Created route: " + name + " (" + id + ")");
        return route;
    }

    public void addStop(String routeId, String stopId, String locationName, int sequence) {
        Route route = routes.get(routeId);
        if (route == null) { System.out.println("[RouteService] Route not found: " + routeId); return; }
        route.addStop(new RouteStop(stopId, locationName, sequence));
        System.out.println("[RouteService] Added stop '" + locationName + "' to route " + route.getName());
    }

    public Route getRoute(String routeId) { return routes.get(routeId); }
}
