package com.utraveler.dao.entity.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Sets;
import com.utraveler.dao.entity.PhotoPushpinEntity;

@Document(collection = "photo_pushpin")
public class PhotoPushpinEntityImpl extends BasePushpinEntity implements PhotoPushpinEntity {

    private long eventId;
    private Collection<Long> photosId;


    public PhotoPushpinEntityImpl() {
        photosId = Sets.newHashSet();
    }


    @Override
    public long getEventId() {
        return eventId;
    }


    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }


    public Collection<Long> getPhotosId() {
        return photosId;
    }


    public void addPhoto(Long photoEntity) {
        photosId.add(photoEntity);
    }


    public boolean deletePhoto(Long photoEntity) {
        return photosId.remove(photoEntity);
    }


    public int getPhotosQuantity() {
        return photosId.size();
    }
}
