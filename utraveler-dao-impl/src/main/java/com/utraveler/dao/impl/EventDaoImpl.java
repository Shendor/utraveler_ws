package com.utraveler.dao.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.utraveler.dao.EventDao;
import com.utraveler.dao.common.impl.MongoDbBaseDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.impl.EventEntityImpl;

public class EventDaoImpl extends MongoDbBaseDao<EventEntity> implements EventDao {

    public EventDaoImpl(MongoOperations mongoOperations) {
        super(mongoOperations);
    }


    @Override
    protected String getTableName() {
        return "event";
    }


    @Override
    public Collection<? extends EventEntity> findEventsOfUser(long userId) {
        Query searchEventByUserIdQuery = new Query(Criteria.where("userId").is(userId));
        return mongoOperations.find(searchEventByUserIdQuery, EventEntityImpl.class);
    }


    @Override
    public EventEntity getById(long id) {
        return mongoOperations.findById(id, EventEntityImpl.class);
    }


    @Override
    public boolean isBelongToUser(long id, long userId) {
        Query query = new Query(Criteria.where("id").is(id).and("userId").is(userId));
        return mongoOperations.count(query, EventEntityImpl.class) > 0;
    }
}
