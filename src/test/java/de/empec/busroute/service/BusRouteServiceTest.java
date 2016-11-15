package de.empec.busroute.service;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusRouteServiceTest {

    @Mock
    private BusRouteRepository busRouteRepository;

    @InjectMocks
    private BusRouteService sut;

    @Test
    public void isDirectIntersectsDisjointSetsToFalse() {
        int depSid = 1;
        int arrSid = 2;
        when(busRouteRepository.getRouteIdsForStationId(depSid)).thenReturn(Optional.of(Sets.newHashSet(1, 2)));
        when(busRouteRepository.getRouteIdsForStationId(arrSid)).thenReturn(Optional.of(Sets.newHashSet(3, 4, 5)));

        assertThat(sut.isDirect(depSid, arrSid), is(false));
    }

    @Test
    public void isDirectIntersectsSharedSetsToTrue() {
        int depSid = 1;
        int arrSid = 2;
        when(busRouteRepository.getRouteIdsForStationId(depSid)).thenReturn(Optional.of(Sets.newHashSet(1, 2, 3)));
        when(busRouteRepository.getRouteIdsForStationId(arrSid)).thenReturn(Optional.of(Sets.newHashSet(3, 4)));

        assertThat(sut.isDirect(depSid, arrSid), is(true));
    }

    @Test
    public void isDirectHandlesMissingDepToFalse() {
        int depSid = 1;
        int arrSid = 2;
        when(busRouteRepository.getRouteIdsForStationId(depSid)).thenReturn(Optional.empty());
        when(busRouteRepository.getRouteIdsForStationId(arrSid)).thenReturn(Optional.of(Sets.newHashSet(3, 4)));

        assertThat(sut.isDirect(depSid, arrSid), is(false));
    }

    @Test
    public void isDirectHandlesMissingArrFalse() {
        int depSid = 1;
        int arrSid = 2;
        when(busRouteRepository.getRouteIdsForStationId(depSid)).thenReturn(Optional.of(Sets.newHashSet(3, 4)));
        when(busRouteRepository.getRouteIdsForStationId(arrSid)).thenReturn(Optional.empty());

        assertThat(sut.isDirect(depSid, arrSid), is(false));
    }
}