package de.empec.busroute.service;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BusRouteServiceTest {

    private BusRouteService sut;

    @Before
    public void setUp() {
        sut = new BusRouteService();
    }

    @Test
    public void isDirectAlwaysFalse() {
        assertThat(sut.isDirect(1, 2), is(true));
    }
}