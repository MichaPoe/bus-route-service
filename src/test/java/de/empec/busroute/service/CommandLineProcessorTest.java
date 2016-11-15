package de.empec.busroute.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.io.File;
import java.net.URL;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineProcessorTest {

    @Mock
    private BusRouteRepository busRouteRepository;

    @InjectMocks
    private CommandLineProcessor sut;

    @Test
    public void doesNotProcessWithoutArgs() throws Exception {
        ApplicationArguments args = new DefaultApplicationArguments(new String[]{});

        sut.run(args);

        verify(busRouteRepository, never()).init(anyObject());
    }

    @Test
    public void processesWithArg() throws Exception {
        URL resource = this.getClass().getClassLoader().getResource("valid.brd");
        File file = new File(resource.toURI());
        ApplicationArguments args = new DefaultApplicationArguments(new String[]{file.getAbsolutePath()});

        sut.run(args);

        verify(busRouteRepository).init(anyObject());
    }
}
