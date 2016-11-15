package de.empec.busroute.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusRouteMain {

    private static final Logger LOG = LoggerFactory.getLogger(BusRouteMain.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BusRouteMain.class, args);
        LOG.debug("started");
    }
}
