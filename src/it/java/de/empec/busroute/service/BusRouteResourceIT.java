package de.empec.busroute.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusRouteResourceIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommandLineProcessor commandLineProcessor;

    @Before
    public void setUpRoutes() throws Exception {
        String validBusRouteFile = createFilePath("valid.brd");
        ApplicationArguments args = new DefaultApplicationArguments(new String[]{validBusRouteFile});
        commandLineProcessor.run(args);
    }

    @Test
    public void directRoute() {
        ResponseEntity<BusRouteRO> response = this.restTemplate.getForEntity("/api/direct?dep_sid={dep_sid}&arr_sid={arr_sid}", BusRouteRO.class, 1, 2);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().isDirectBusRoute(), is(true));
    }

    @Test
    public void missingRoute() {
        ResponseEntity<BusRouteRO> response = this.restTemplate.getForEntity("/api/direct?dep_sid={dep_sid}&arr_sid={arr_sid}", BusRouteRO.class, 1, 3);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().isDirectBusRoute(), is(false));
    }

    @Test
    public void missingArr() {
        ResponseEntity<BusRouteRO> response = this.restTemplate.getForEntity("/api/direct?dep_sid={dep_sid}&arr_sid={arr_sid}", BusRouteRO.class, 99, 3);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().isDirectBusRoute(), is(false));
    }

    @Test
    public void missingDep() {
        ResponseEntity<BusRouteRO> response = this.restTemplate.getForEntity("/api/direct?dep_sid={dep_sid}&arr_sid={arr_sid}", BusRouteRO.class, 2, 99);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().isDirectBusRoute(), is(false));
    }

    private String createFilePath(String fileName) throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        File file = new File(resource.toURI());
        return file.getAbsolutePath();
    }
}