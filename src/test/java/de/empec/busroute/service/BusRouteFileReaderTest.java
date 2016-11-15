package de.empec.busroute.service;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class BusRouteFileReaderTest {

    @Test
    public void canProcessValidFile() throws URISyntaxException {
        BusRouteFileReader sut = createSut("valid.brd");
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
        BusRouteFileReader sut = createSut("invalidWrongNumberOfRoutes.brd");
        assertThat(sut.getRouteIdsForStationId().keySet(), hasSize(1));
        assertThat(sut.getRouteIdsForStationId().get(0), hasSize(5));
        assertThat(sut.getRouteIdsForStationId().get(0), hasItems(0, 1, 2, 3, 4));
    }

    @Test
    public void simplyStopsAtFirstNonInteger() throws URISyntaxException {
        BusRouteFileReader sut = createSut("invalidNoInteger.brd");
        assertThat(sut.getRouteIdsForStationId().keySet(), hasSize(1));
        assertThat(sut.getRouteIdsForStationId().get(0), hasSize(2));
        assertThat(sut.getRouteIdsForStationId().get(0), hasItems(0, 1));
    }

    @Test
    public void simplySkipsRoutesWithNotEnoughStations() throws URISyntaxException {
        BusRouteFileReader sut = createSut("notEnoughStations.brd");
        assertThat(sut.getRouteIdsForStationId().keySet(), hasSize(1));
        assertThat(sut.getRouteIdsForStationId().get(0), hasSize(2));
        assertThat(sut.getRouteIdsForStationId().get(0), hasItems(1, 2));
    }

    private BusRouteFileReader createSut(String fileName) throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        File file = new File(resource.toURI());
        return new BusRouteFileReader(Paths.get(file.getAbsolutePath()));
    }
}