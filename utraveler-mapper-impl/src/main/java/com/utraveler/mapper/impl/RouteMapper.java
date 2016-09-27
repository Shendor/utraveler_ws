package com.utraveler.mapper.impl;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.entity.RoutePushpinEntity;
import com.utraveler.dao.entity.impl.RouteEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Color;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.Route;
import com.utraveler.model.RoutePushpin;

public class RouteMapper implements Mapper<Route, RouteEntity> {

    private Mapper<RoutePushpin, RoutePushpinEntity> routePushpinMapper;


    public void setRoutePushpinMapper(Mapper<RoutePushpin, RoutePushpinEntity> routePushpinMapper) {
        this.routePushpinMapper = routePushpinMapper;
    }


    @Override
    public RouteEntity mapModel(Route model) {
        RouteEntityImpl routeEntity = new RouteEntityImpl();
        routeEntity.setId(model.getId());
        routeEntity.setName(model.getName());
        routeEntity.setDescription(model.getDescription());
//        routeEntity.setCoordinates(convertToArrayOfCoordinates(model));

        if (model.getColor() != null) {
            routeEntity.setColor(new byte[]{model.getColor().getR(), model.getColor().getG(), model.getColor().getB()});
        }
        model.getPushpins().forEach((item) -> routeEntity.addPushpin(routePushpinMapper.mapModel(item)));

        return routeEntity;
    }


    @Override
    public Route mapEntity(RouteEntity entity) {
        Route route = new Route();
        route.setId(entity.getId());
        route.setName(entity.getName());
        route.setDescription(entity.getDescription());
        route.setColor(new Color(entity.getColor()[0], entity.getColor()[1], entity.getColor()[2]));
        route.setCoordinates(convertToCollectionOfGeoCoordinates(entity));

        addPushpins(entity, route);

        return route;
    }


    private void addPushpins(RouteEntity entity, Route route) {
        if (entity instanceof RouteEntityImpl) {
            Collection<RoutePushpin> pushpins = Lists.newArrayList();
            ((RouteEntityImpl)entity).getPushpins().forEach((item) -> pushpins.add(routePushpinMapper.mapEntity(item)));
            route.setPushpins(pushpins);
        }
    }


    private Collection<GeoCoordinate> convertToCollectionOfGeoCoordinates(RouteEntity routeEntity) {
        Collection<GeoCoordinate> geoCoordinates = Lists.newArrayList();
        /*double[] coordinates = routeEntity.getCoordinates();
        if (coordinates != null) {
            for (int i = 0; i < coordinates.length - 1; i += 2) {
                geoCoordinates.add(new GeoCoordinate(coordinates[i], coordinates[i + 1]));
            }
        }*/
        return geoCoordinates;
    }


    private double[] convertToArrayOfCoordinates(Route model) {
        int coordinatesQuantity = model.getCoordinates().size();
        double[] coordinates = new double[2 * coordinatesQuantity];
        int index = 0;
        for (GeoCoordinate coordinate : model.getCoordinates()) {
            coordinates[index++] = coordinate.getLatitude();
            coordinates[index++] = coordinate.getLongitude();
        }
        return coordinates;
    }
}
