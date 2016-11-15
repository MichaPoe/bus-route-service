package de.empec.busroute.service;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public class BusRouteRepository {

    private Map<Integer, Set<Integer>> routeIdsForStationId;

    public void init(Map<Integer, Set<Integer>> routeIdsForStationId) {
        this.routeIdsForStationId = routeIdsForStationId;
    }

    public Set<Integer> getRouteIdsForStationId(int stationId) {
        return routeIdsForStationId.get(stationId);
    }
}
