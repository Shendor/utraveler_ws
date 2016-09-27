package com.utraveler.dao;

import java.util.Collection;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.PhotoEntity;

public interface PhotoDao extends Dao<PhotoEntity>, DeleteEventDao {

    Collection<? extends PhotoEntity> findPhotosOfEvent(long eventId);


    Collection<? extends PhotoEntity> findPhotosOfPushpin(long pushpinId);


    Collection<Long> findPhotosIdOfPushpins(long eventId);


    PhotoEntity findPhotoOfEvent(long photoId, long eventId);


    int getPhotosQuantityOfUser(long userId);


    long getPhotosQuantity(long eventId);
}
