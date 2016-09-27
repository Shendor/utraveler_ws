package com.utraveler.dao.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.utraveler.dao.PhotoPushpinDao;
import com.utraveler.dao.common.impl.MongoDbBaseDao;
import com.utraveler.dao.entity.PhotoPushpinEntity;
import com.utraveler.dao.entity.impl.EventEntityImpl;
import com.utraveler.dao.entity.impl.PhotoEntityImpl;
import com.utraveler.dao.entity.impl.PhotoPushpinEntityImpl;

public class PhotoPushpinDaoImpl extends MongoDbBaseDao<PhotoPushpinEntity> implements PhotoPushpinDao {


    public PhotoPushpinDaoImpl(MongoOperations mongoOperations) {
        super(mongoOperations);
    }


    @Override
    protected String getTableName() {
        return "photo_pushpin";
    }


    @Override
    public Collection<? extends PhotoPushpinEntity> getPushpinsOfEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        query.fields().exclude("photosId");
        return mongoOperations.find(query, PhotoPushpinEntityImpl.class);
    }


    @Override
    public void addPhotoToPushpin(long photoId, long pushpinId) {
        PhotoPushpinEntityImpl pushpinEntity = mongoOperations.findById(pushpinId, PhotoPushpinEntityImpl.class);
        PhotoEntityImpl photoEntity = mongoOperations.findById(photoId, PhotoEntityImpl.class);
        if (pushpinEntity != null && photoEntity != null) {
            pushpinEntity.addPhoto(photoEntity.getId());
            update(pushpinEntity);
        }
    }


    @Override
    public void deletePhotoFromPushpin(long photoId, long pushpinId) {
        PhotoPushpinEntityImpl pushpinEntity = mongoOperations.findById(pushpinId, PhotoPushpinEntityImpl.class);
        if (pushpinEntity != null) {
            pushpinEntity.deletePhoto(photoId);
            update(pushpinEntity);
        }
    }


    @Override
    public void deletePhotoFromPushpins(long photoId) {

    }


    @Override
    public void deleteFromEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        mongoOperations.remove(query, PhotoPushpinEntityImpl.class);
    }


    @Override
    public void deleteFromEvent(Collection<Long> entitiesId, long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId).and("id").in(entitiesId));
        mongoOperations.remove(query, PhotoPushpinEntityImpl.class);
    }


    @Override
    public int getPhotosQuantity(long pushpinId) {
        PhotoPushpinEntityImpl pushpinEntity = mongoOperations.findById(pushpinId, PhotoPushpinEntityImpl.class);
        return pushpinEntity.getPhotosQuantity();
    }


    @Override
    public long findFirstPushpinIdForPhoto(long photoId) {
        return 0;
    }


    @Override
    public boolean isPushpinOfUser(long pushpinId, long userId) {
        boolean isPushpinOfUser = false;
        Query query = Query.query(Criteria.where("pushpinId").is(pushpinId));
        query.fields().include("eventId");
        PhotoPushpinEntityImpl pushpinEntity = mongoOperations.findOne(query, PhotoPushpinEntityImpl.class);
        if (pushpinEntity != null) {
            Query queryForEvent = Query.query(Criteria.where("pushpinId").is(pushpinId));
            queryForEvent.fields().include("userId");
            EventEntityImpl eventEntity = mongoOperations.findById(pushpinEntity.getEventId(), EventEntityImpl.class);
            isPushpinOfUser = eventEntity.getUserId() == userId;
        }
        return isPushpinOfUser;
    }


    @Override
    public PhotoPushpinEntity getById(long id) {
        return mongoOperations.findById(id, PhotoPushpinEntityImpl.class);
    }

}
