package com.utraveler.dao.mysql;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.utraveler.dao.RouteDao;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.RouteEntityImpl;

@Repository("routeDao")
public class RouteDaoImpl extends BaseDao<RouteEntity> implements RouteDao {

    @Override
    public Collection<? extends RouteEntity> findRoutesOfEvent(long eventId) {
        Query query = getSession().getNamedQuery("Route.findRoutesOfEvent");
        query.setLong("eventId", eventId);
        return query.list();
    }


    @Override
    public RouteEntity findRouteOfEvent(long routeId, long eventId) {
        Query query = getSession().getNamedQuery("Route.findRouteOfEvent");
        query.setLong("id", routeId);
        query.setLong("eventId", eventId);
        return (RouteEntity)query.uniqueResult();
    }


    @Override
    public RouteEntity getById(long id) {
        return (RouteEntity)getSession().get(RouteEntityImpl.class, id);
    }


    @Override
    public boolean deleteFromEvent(long eventId) {
        Query query = getSession().getNamedQuery("Route.deleteFromEvent");
        query.setLong("eventId", eventId);
        return query.executeUpdate() > 0;
    }


    @Override
    public boolean deleteFromEvent(Collection<Long> routesId, long eventId) {
        Query query = getSession().getNamedQuery("Route.deleteRoutesFromEvent");
        query.setLong("eventId", eventId);
        query.setParameterList("routesId", routesId);
        return query.executeUpdate() > 0;
    }


    @Override
    public long getRoutesQuantity(long eventId) {
        Query query = getSession().getNamedQuery("Route.getRoutesQuantity");
        query.setLong("eventId", eventId);

        return (Long)query.uniqueResult();
    }
}
