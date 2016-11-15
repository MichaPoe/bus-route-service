package de.empec.busroute.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class BusRouteFileReader {

    private static final Logger LOG = LoggerFactory.getLogger(BusRouteFileReader.class);

    private Path file;
    private int numberOfRoutes;
    private Map<Integer, Set<Integer>> routeIdsForStationId;

    public BusRouteFileReader(Path file) {
        this.file = file;
        read();
    }

    private void read() {
        routeIdsForStationId = Maps.newHashMap();;
        LOG.debug("file to process: {}", file.toAbsolutePath().toString());

        scanFile();

        int numberOfRoutesRead = routeIdsForStationId.keySet().size();
        if (numberOfRoutes != numberOfRoutesRead) {
            LOG.warn("number of expected routes {} differs from routes read {}", numberOfRoutes, numberOfRoutesRead);
        }
    }

    public Map<Integer, Set<Integer>> getRouteIdsForStationId() {
        return routeIdsForStationId;
    }

    private void scanFile() {
        try (Scanner fileScanner = new Scanner(file)) {
            numberOfRoutes = fileScanner.nextInt();
            LOG.debug("Number of routes: {}", numberOfRoutes);
            String skippedContent = fileScanner.nextLine();
            if (!StringUtils.isEmpty(skippedContent)) {
                LOG.warn("skipped first line unusable content '{}'", skippedContent);
            }

            while (fileScanner.hasNext()) {
                scanRouteLine(fileScanner.nextLine());
            }

            LOG.debug("result: {}", routeIdsForStationId);
        } catch (IOException e) {
            LOG.error("exception while reading bus route file", e);
        }
    }

    private void scanRouteLine(String routeLine) {
        Scanner lineScanner = new Scanner(routeLine);
        int busRouteId = lineScanner.nextInt();
        LOG.debug("Route {}: ", busRouteId);

        Set<Integer> stationIds = Sets.newHashSet();
        while (lineScanner.hasNextInt()) {
            int stationId = lineScanner.nextInt();
            LOG.debug("  {}", stationId);
            stationIds.add(stationId);
        }

        if (stationIds.size() < 2) {
            LOG.warn("not at least two stations for route: {} (skipping)", routeLine);
        } else {
            stationIds.forEach(i -> {
                if (!routeIdsForStationId.containsKey(i)) {
                    routeIdsForStationId.put(i, Sets.newHashSet());
                }
                routeIdsForStationId.get(i).add(busRouteId);
            });
        }
    }

}
