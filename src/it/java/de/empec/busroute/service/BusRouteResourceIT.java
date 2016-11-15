package de.empec.busroute.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusRouteResourceIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void exampleTest() {
        ResponseEntity<BusRouteRO> response = this.restTemplate.getForEntity("/api/direct?dep_sid={dep_sid}&arr_sid={arr_sid}", BusRouteRO.class, 1, 2);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().isDirectBusRoute(), is(true));
    }
}