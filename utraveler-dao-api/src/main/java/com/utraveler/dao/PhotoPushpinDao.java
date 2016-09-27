package com.utraveler.dao;

import java.util.Collection;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.common.DeleteManyDao;
import com.utraveler.dao.entity.PhotoPushpinEntity;

public interface PhotoPushpinDao extends Dao<PhotoPushpinEntity>, DeleteEventDao {

    Collection<? extends PhotoPushpinEntity> getPushpinsOfEvent(long eventId);


    void addPhotoToPushpin(long photoId, long pushpinId);


    void deletePhotoFromPushpin(long photoId, long pushpinId);


    void deletePhotoFromPushpins(long photoId);


    int getPhotosQuantity(long pushpinId);


    long findFirstPushpinIdForPhoto(long photoId);


    boolean isPushpinOfUser(long pushpinId, long userId);

}
