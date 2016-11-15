package de.empec.busroute.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class CommandLineProcessor implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CommandLineProcessor.class);

    private BusRouteRepository busRouteRepository;

    @Autowired
    public CommandLineProcessor(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        String[] args = applicationArguments.getSourceArgs();
        if (args.length < 1) {
            LOG.error("Missing file name to read bus routes from! Will start anyway but results could be disappointing :(");
        } else {
            BusRouteFileReader busRouteFileReader = new BusRouteFileReader(Paths.get(args[0]));
            busRouteRepository.init(busRouteFileReader.getRouteIdsForStationId());
            LOG.info("Initialized repository from bus route file {}", args[0]);
        }
    }
}
