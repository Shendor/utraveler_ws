package com.utraveler.dao.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.utraveler.dao.MessageDao;
import com.utraveler.dao.common.impl.MongoDbBaseDao;
import com.utraveler.dao.entity.MessageEntity;
import com.utraveler.dao.entity.impl.MessageEntityImpl;

public class MessageDaoImpl extends MongoDbBaseDao<MessageEntity> implements MessageDao {

    public MessageDaoImpl(MongoOperations mongoOperations) {
        super(mongoOperations);
    }


    @Override
    public Collection<? extends MessageEntity> getMessagesOfEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        return mongoOperations.find(query, MessageEntityImpl.class);
    }


    @Override
    protected String getTableName() {
        return "message";
    }


    @Override
    public MessageEntity getById(long id) {
        return mongoOperations.findById(id, MessageEntityImpl.class);
    }


    @Override
    public void deleteFromEvent(long eventId) {

    }


    @Override
    public void deleteFromEvent(Collection<Long> entitiesId, long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId).and("id").in(entitiesId));
        mongoOperations.remove(query, MessageEntityImpl.class);
    }
}
