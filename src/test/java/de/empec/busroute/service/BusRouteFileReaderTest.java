package de.empec.busroute.service;

import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class BusRouteFileReaderTest {

    @Test
    public void canProcessValidFile() throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource("valid.brd");
        BusRouteFileReader sut = new BusRouteFileReader(Paths.get(resource.toURI()));
        assertThat(sut.getRouteIdsForStationId().keySet(), hasSize(3));
        assertThat(sut.getRouteIdsForStationId().get(0), hasSize(3));
        assertThat(sut.getRouteIdsForStationId().get(0), hasItems(0, 1, 2));
        assertThat(sut.getRouteIdsForStationId().get(1), hasSize(3));
        assertThat(sut.getRouteIdsForStationId().get(1), hasItems(3, 1, 6));
        assertThat(sut.getRouteIdsForStationId().get(2), hasSize(3));
        assertThat(sut.getRouteIdsForStationId().get(2), hasItems(0, 6, 4));
    }

    @Test
    public void canProcessEvenWithWrongNumberOfRoutes() throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource("invalidWrongNumberOfRoutes.brd");
        BusRouteFileReader sut = new BusRouteFileReader(Paths.get(resource.toURI()));
        assertThat(sut.getRouteIdsForStationId().keySet(), hasSize(1));
        assertThat(sut.getRouteIdsForStationId().get(0), hasSize(5));
        assertThat(sut.getRouteIdsForStationId().get(0), hasItems(0, 1, 2, 3, 4));
    }

    @Test
    public void simplyStopsAtFirstNonInteger() throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource("invalidNoInteger.brd");
        BusRouteFileReader sut = new BusRouteFileReader(Paths.get(resource.toURI()));
        assertThat(sut.getRouteIdsForStationId().keySet(), hasSize(1));
        assertThat(sut.getRouteIdsForStationId().get(0), hasSize(2));
        assertThat(sut.getRouteIdsForStationId().get(0), hasItems(0, 1));
    }

    @Test
    public void simplySkipsRoutesWithNotEnoughStations() throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource("notEnoughStations.brd");
        BusRouteFileReader sut = new BusRouteFileReader(Paths.get(resource.toURI()));
        assertThat(sut.getRouteIdsForStationId().keySet(), hasSize(1));
        assertThat(sut.getRouteIdsForStationId().get(0), hasSize(2));
        assertThat(sut.getRouteIdsForStationId().get(0), hasItems(1, 2));
    }
}