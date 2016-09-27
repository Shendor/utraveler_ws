package com.utraveler.mapper.impl;

import com.utraveler.dao.entity.PhotoPushpinEntity;
import com.utraveler.dao.entity.impl.PhotoPushpinEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.Pushpin;

public class PhotoPushpinMapper implements Mapper<Pushpin, PhotoPushpinEntity> {

    @Override
    public PhotoPushpinEntity mapModel(Pushpin model) {
        PhotoPushpinEntityImpl photoPushpinEntity = new PhotoPushpinEntityImpl();
        photoPushpinEntity.setId(model.getId());
        photoPushpinEntity.setLatitude(model.getCoordinate().getLatitude());
        photoPushpinEntity.setLongitude(model.getCoordinate().getLongitude());
        photoPushpinEntity.setThumbnail(model.getThumbnail());

        return photoPushpinEntity;
    }


    @Override
    public Pushpin mapEntity(PhotoPushpinEntity entity) {
        Pushpin pushpin = new Pushpin();
        pushpin.setId(entity.getId());
        pushpin.setCoordinate(new GeoCoordinate(entity.getLatitude(), entity.getLongitude()));
        pushpin.setThumbnail(entity.getThumbnail());

        return pushpin;
    }
}
