package com.utraveler.mapper.mysql;

import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.mysql.entity.RouteEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Route;

public class RouteMapper implements Mapper<Route, RouteEntity> {


    @Override
    public RouteEntity mapModel(Route model) {
        RouteEntityImpl routeEntity = new RouteEntityImpl();
        routeEntity.setId(model.getId());
        routeEntity.setName(model.getName());
        routeEntity.setDescription(model.getDescription());
        routeEntity.setCoordinatesJson(model.getCoordinatesJson());
        routeEntity.setPolygonsJson(model.getPolygonsJson());
        routeEntity.setPushpinsJson(model.getPushpinsJson());
        routeEntity.setChangeDate(model.getChangeDate());
        if (model.getColor() != null) {
            routeEntity.setColor(model.getColor());
        }

        return routeEntity;
    }


    @Override
    public Route mapEntity(RouteEntity entity) {
        Route route = new Route();
        route.setId(entity.getId());
        route.setName(entity.getName());
        route.setDescription(entity.getDescription());
        route.setColor(entity.getColor());
        route.setCoordinatesJson(entity.getCoordinatesJson());
        route.setPushpinsJson(entity.getPushpinsJson());
        route.setPolygonsJson(entity.getPolygonsJson());
        route.setChangeDate(route.getChangeDate());

        return route;
    }
}
