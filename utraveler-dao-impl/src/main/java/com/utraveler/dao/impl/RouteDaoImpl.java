package com.utraveler.dao.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.utraveler.dao.RouteDao;
import com.utraveler.dao.common.impl.MongoDbBaseDao;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.entity.impl.RouteEntityImpl;

public class RouteDaoImpl extends MongoDbBaseDao<RouteEntity> implements RouteDao {

    public RouteDaoImpl(MongoOperations mongoOperations) {
        super(mongoOperations);
    }


    @Override
    protected String getTableName() {
        return "route";
    }


    @Override
    public Collection<? extends RouteEntity> getRoutesOfEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        query.fields().exclude("coordinates");
        query.fields().exclude("pushpins");
        return mongoOperations.find(query, RouteEntityImpl.class);
    }


    @Override
    public RouteEntity getById(long id) {
        return mongoOperations.findById(id, RouteEntityImpl.class);
    }


    @Override
    public void deleteFromEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        mongoOperations.remove(query, RouteEntityImpl.class);
    }


    @Override
    public void deleteFromEvent(Collection<Long> entitiesId, long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId).and("id").in(entitiesId));
        mongoOperations.remove(query, RouteEntityImpl.class);
    }
}
