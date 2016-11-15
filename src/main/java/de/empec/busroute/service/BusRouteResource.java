package de.empec.busroute.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = BusRouteResource.BASE, produces = {APPLICATION_JSON_VALUE})
public class BusRouteResource {

    private static final Logger LOG = LoggerFactory.getLogger(BusRouteResource.class);
    public static final String BASE = "/direct";

    private BusRouteService busRouteService;

    @Autowired
    public BusRouteResource(BusRouteService busRouteService) {
        this.busRouteService = busRouteService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BusRouteRO> isDirect(
            @RequestParam(value = "dep_sid") int depSid,
            @RequestParam(value = "arr_sid") int arrSid
    ) {
        boolean direct = busRouteService.isDirect(depSid, arrSid);
        LOG.debug("isDirect dep={}, arr={} -> {}", depSid, arrSid, direct);
        BusRouteRO result = BusRouteROBuilder.aBusRouteRO().withDepSid(depSid).withArrSid(arrSid).withDirectBusRoute(direct).build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
