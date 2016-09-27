package com.utraveler.dao.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.common.impl.MongoDbBaseDao;
import com.utraveler.dao.entity.PhotoEntity;
import com.utraveler.dao.entity.impl.PhotoEntityImpl;
import com.utraveler.dao.entity.impl.PhotoPushpinEntityImpl;

public class PhotoDaoImpl extends MongoDbBaseDao<PhotoEntity> implements PhotoDao {

    public PhotoDaoImpl(MongoOperations mongoOperations) {
        super(mongoOperations);
    }


    @Override
    protected String getTableName() {
        return "photo";
    }


    @Override
    public Collection<? extends PhotoEntity> getPhotosOfEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        return mongoOperations.find(query, PhotoEntityImpl.class);
    }


    @Override
    public Collection<? extends PhotoEntity> getPhotosOfPushpin(long pushpinId) {
        PhotoPushpinEntityImpl pushpinEntity = mongoOperations.findById(pushpinId, PhotoPushpinEntityImpl.class);
        if (pushpinEntity != null) {
            Query query = Query.query(Criteria.where("eventId").is(pushpinEntity.getEventId()).and("id").in(pushpinEntity.getPhotosId()));
            return mongoOperations.find(query, PhotoEntityImpl.class);
        }
        return null;
    }


    @Override
    public Collection<Long> getPhotosIdOfPushpins(long eventId) {
        return null;
    }


    @Override
    public void deleteFromEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        mongoOperations.remove(query, PhotoEntityImpl.class);
    }


    @Override
    public PhotoEntity getById(long id) {
        return mongoOperations.findById(id, PhotoEntityImpl.class);
    }


    @Override
    public void deleteFromEvent(Collection<Long> entitiesId, long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId).and("id").in(entitiesId));
        mongoOperations.remove(query, PhotoEntityImpl.class);
    }
}
