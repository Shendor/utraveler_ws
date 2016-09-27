package com.utraveler.dao.impl;

import java.util.Collection;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.PhotoPushpinDao;
import com.utraveler.dao.common.MongoDbTestFixture;
import com.utraveler.dao.entity.PhotoPushpinEntity;
import com.utraveler.dao.entity.impl.PhotoEntityImpl;
import com.utraveler.dao.entity.impl.PhotoPushpinEntityImpl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class PhotoPushpinDaoImplTest extends MongoDbTestFixture {

    private PhotoPushpinDao photoPushpinDao;
    private PhotoDao photoDao;


    @BeforeMethod
    public void setUp() {
        photoDao = new PhotoDaoImpl(mongoTemplate);
        photoPushpinDao = new PhotoPushpinDaoImpl(mongoTemplate);
    }


    @AfterMethod
    public void tearDown() {
        mongoTemplate.dropCollection(PhotoEntityImpl.class);
        mongoTemplate.dropCollection(PhotoPushpinEntityImpl.class);
    }


    @Test
    public void testGetPushpinsOfEvent_AllEntitiesValid_ReturnPushpinsExcludedPhotos() {
        int eventId = 100;

        PhotoEntityImpl photoEntity = createAndInsertPhoto();

        PhotoPushpinEntityImpl pushpinEntity = createPhotoPushpin(1, eventId);
        pushpinEntity.addPhoto(photoEntity.getId());
        photoPushpinDao.insert(pushpinEntity);

        Collection<? extends PhotoPushpinEntity> pushpins = photoPushpinDao.getPushpinsOfEvent(eventId);
        assertEquals(1, pushpins.size());
    }


    @Test
    public void testAddPhotoToPushpin_AllEntitiesValid_PushpinUpdated() {

        PhotoEntityImpl photoEntity = createAndInsertPhoto();

        PhotoPushpinEntityImpl pushpinEntity = createPhotoPushpin(10000, 100);
        photoPushpinDao.insert(pushpinEntity);
        photoPushpinDao.addPhotoToPushpin(photoEntity.getId(), pushpinEntity.getId());

        PhotoPushpinEntityImpl foundPushpin = (PhotoPushpinEntityImpl)photoPushpinDao.getById(pushpinEntity.getId());
        assertTrue(foundPushpin.getPhotosId().iterator().hasNext());
    }


    @Test
    public void testDeletePhotoFromPushpin_AllEntitiesValid_PushpinUpdated() {

        PhotoEntityImpl photoEntity = createAndInsertPhoto();

        PhotoPushpinEntityImpl pushpinEntity = createPhotoPushpin(1000, 100);
        photoPushpinDao.insert(pushpinEntity);
        photoPushpinDao.addPhotoToPushpin(photoEntity.getId(), pushpinEntity.getId());
        photoPushpinDao.deletePhotoFromPushpin(photoEntity.getId(), pushpinEntity.getId());

        PhotoPushpinEntityImpl foundPushpin = (PhotoPushpinEntityImpl)photoPushpinDao.getById(pushpinEntity.getId());
        assertFalse(foundPushpin.getPhotosId().iterator().hasNext());
    }


    @Test
    public void testDeletePushpinsFromEvent_AllEntitiesValid_PushpinsDeleted() {

        int eventId = 100;

        photoPushpinDao.insert(createPhotoPushpin(1, eventId));
        photoPushpinDao.insert(createPhotoPushpin(2, eventId));

        photoPushpinDao.deleteFromEvent(eventId);

        assertNull(photoPushpinDao.getById(1));
        assertNull(photoPushpinDao.getById(2));
    }


    @Test
    public void testGetPhotosQuantityOfPushpin_AllEntitiesValid_ReturnCorrectQuantity() {

        PhotoEntityImpl photoEntity = createAndInsertPhoto();

        PhotoPushpinEntityImpl pushpinEntity = createPhotoPushpin(1, 100);
        photoPushpinDao.insert(pushpinEntity);
        photoPushpinDao.addPhotoToPushpin(photoEntity.getId(), pushpinEntity.getId());

        assertEquals(1, photoPushpinDao.getPhotosQuantity(pushpinEntity.getId()));
    }


    private PhotoPushpinEntityImpl createPhotoPushpin(int id, int eventId) {
        PhotoPushpinEntityImpl pushpinEntity = new PhotoPushpinEntityImpl();
        pushpinEntity.setId(id);
        pushpinEntity.setEventId(eventId);
        return pushpinEntity;
    }


    private PhotoEntityImpl createAndInsertPhoto() {
        PhotoEntityImpl photoEntity = new PhotoEntityImpl();
        photoEntity.setId(1);
        photoEntity.setName("Photo1");
        photoDao.insert(photoEntity);
        return photoEntity;
    }
}
