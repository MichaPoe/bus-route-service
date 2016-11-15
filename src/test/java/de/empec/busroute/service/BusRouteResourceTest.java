package de.empec.busroute.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BusRouteResourceTest {

    @Mock
    private BusRouteService busRouteService;

    @InjectMocks
    private BusRouteResource sut;

    @Test
    public void isDirectStatusOk() {
        ResponseEntity<BusRouteRO> result = sut.isDirect(1, 2);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void isDirectDelegatesToService() {
        sut.isDirect(3, 4);

        verify(busRouteService).isDirect(3, 4);
    }
}