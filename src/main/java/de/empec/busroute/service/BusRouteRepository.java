package de.empec.busroute.service;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class BusRouteRepository {

    private Map<Integer, Set<Integer>> routeIdsForStationId;

    public void init(Map<Integer, Set<Integer>> routeIdsForStationId) {
        this.routeIdsForStationId = routeIdsForStationId;
    }

    public Optional<Set<Integer>> getRouteIdsForStationId(int stationId) {
        return Optional.ofNullable(routeIdsForStationId.get(stationId));
    }
}
