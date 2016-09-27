package com.utraveler.dao.mysql;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.entity.PhotoEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.PhotoEntityImpl;

@Repository("photoDao")
public class PhotoDaoImpl extends BaseDao<PhotoEntity> implements PhotoDao {

    @Override
    public Collection<? extends PhotoEntity> findPhotosOfEvent(long eventId) {
        Query query = getSession().getNamedQuery("Photo.findPhotosOfEvent");
        query.setLong("eventId", eventId);
        return query.list();
    }


    @Override
    public Collection<? extends PhotoEntity> findPhotosOfPushpin(long pushpinId) {
        Query query = getSession().getNamedQuery("Photo.findPhotosOfPushpin");
        query.setLong("pushpinId", pushpinId);
        return query.list();
    }


    @Override
    public Collection<Long> findPhotosIdOfPushpins(long eventId) {
        return null;
    }


    @Override
    public PhotoEntity findPhotoOfEvent(long photoId, long eventId) {
        Query query = getSession().getNamedQuery("Photo.findPhotoOfEvent");
        query.setLong("id", photoId);
        query.setLong("eventId", eventId);
        return (PhotoEntity)query.uniqueResult();
    }


    @Override
    public int getPhotosQuantityOfUser(long userId) {
        Query query = getSession().getNamedQuery("Photo.getPhotosQuantityOfUser");
        query.setLong("userId", userId);
        return ((Long)query.uniqueResult()).intValue();
    }


    @Override
    public PhotoEntity getById(long id) {
        return (PhotoEntity)getSession().get(PhotoEntityImpl.class, id);
    }


    @Override
    public boolean deleteFromEvent(long eventId) {
        Query query = getSession().getNamedQuery("Photo.deleteFromEvent");
        query.setLong("eventId", eventId);
        return query.executeUpdate() > 0;
    }


    @Override
    public boolean deleteFromEvent(Collection<Long> photosId, long eventId) {
        Query query = getSession().getNamedQuery("Photo.deletePhotosFromEvent");
        query.setLong("eventId", eventId);
        query.setParameterList("photosId", photosId);
        return query.executeUpdate() > 0;
    }


    @Override
    public long getPhotosQuantity(long eventId) {
        Query query = getSession().getNamedQuery("Photo.getPhotosQuantity");
        query.setLong("eventId", eventId);

        return (Long)query.uniqueResult();
    }
}
