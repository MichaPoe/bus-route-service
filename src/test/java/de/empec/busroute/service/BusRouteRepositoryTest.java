package de.empec.busroute.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BusRouteRepositoryTest {

    @Test
    public void initSetsRepo() {
        HashMap<Integer, Set<Integer>> routeIdsForStationId = Maps.newHashMap();
        routeIdsForStationId.put(0, Sets.newHashSet(1, 2));
        BusRouteRepository sut = new BusRouteRepository();

        sut.init(routeIdsForStationId);

        assertThat(sut.getRouteIdsForStationId(0).isPresent(), is(true));
        assertThat(sut.getRouteIdsForStationId(0).get(), hasItems(1, 2));
    }

    @Test
    public void unknownStationReturnsEmptySet() {
        HashMap<Integer, Set<Integer>> routeIdsForStationId = Maps.newHashMap();
        routeIdsForStationId.put(0, Sets.newHashSet(1, 2));
        BusRouteRepository sut = new BusRouteRepository();

        sut.init(routeIdsForStationId);

        assertThat(sut.getRouteIdsForStationId(1).isPresent(), is(false));
    }
}