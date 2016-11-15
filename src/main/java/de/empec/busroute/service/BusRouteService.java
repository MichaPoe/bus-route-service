package de.empec.busroute.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusRouteService {

    private static final Logger LOG = LoggerFactory.getLogger(BusRouteService.class);

    // ToDo use repo

    public boolean isDirect(int depSid, int arrSid) {
        boolean result = true;
        LOG.info("isDirect dep={}, arr={} -> {}", depSid, arrSid, result);
        return result;
    }
}
