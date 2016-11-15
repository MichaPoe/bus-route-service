package de.empec.busroute.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class BusRouteService {

    private static final Logger LOG = LoggerFactory.getLogger(BusRouteService.class);

    private BusRouteRepository busRouteRepository;

    @Autowired
    public BusRouteService(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    public boolean isDirect(int depSid, int arrSid) {
        Optional<Set<Integer>> depRouteIdsOptional = busRouteRepository.getRouteIdsForStationId(depSid);
        if (!depRouteIdsOptional.isPresent()) {
            LOG.debug("no routes found for dep {}", depSid);
            return false;
        }
        Optional<Set<Integer>> arrRouteIdsOptional = busRouteRepository.getRouteIdsForStationId(arrSid);
        if (!arrRouteIdsOptional.isPresent()) {
            LOG.debug("no routes found for arr {}", arrSid);
            return false;
        }

        Set<Integer> depRouteIds = depRouteIdsOptional.get();
        Set<Integer> arrRouteIds = arrRouteIdsOptional.get();

        boolean result = !Collections.disjoint(depRouteIds, arrRouteIds);
        LOG.debug("routes for dep {} are {}", depSid, depRouteIds);
        LOG.debug("routes for arr {} are {}", arrSid, arrRouteIds);

        LOG.info("isDirect dep={}, arr={} -> {}", depSid, arrSid, result);
        return result;
    }
}
