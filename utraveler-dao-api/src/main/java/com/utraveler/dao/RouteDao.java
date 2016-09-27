package com.utraveler.dao;

import java.util.Collection;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.RouteEntity;

public interface RouteDao extends Dao<RouteEntity>, DeleteEventDao {

    Collection<? extends RouteEntity> findRoutesOfEvent(long eventId);


    RouteEntity findRouteOfEvent(long routeId, long eventId);


    long getRoutesQuantity(long eventId);
}
