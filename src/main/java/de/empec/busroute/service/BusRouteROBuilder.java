package de.empec.busroute.service;

public final class BusRouteROBuilder {
    private int depSid;
    private int arrSid;
    private boolean directBusRoute;

    private BusRouteROBuilder() {
    }

    public static BusRouteROBuilder aBusRouteRO() {
        return new BusRouteROBuilder();
    }

    public BusRouteROBuilder withDepSid(int depSid) {
        this.depSid = depSid;
        return this;
    }

    public BusRouteROBuilder withArrSid(int arrSid) {
        this.arrSid = arrSid;
        return this;
    }

    public BusRouteROBuilder withDirectBusRoute(boolean directBusRoute) {
        this.directBusRoute = directBusRoute;
        return this;
    }

    public BusRouteRO build() {
        BusRouteRO busRouteRO = new BusRouteRO();
        busRouteRO.setDepSid(depSid);
        busRouteRO.setArrSid(arrSid);
        busRouteRO.setDirectBusRoute(directBusRoute);
        return busRouteRO;
    }
}
