package com.utraveler.dao.impl;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.PhotoPushpinDao;
import com.utraveler.dao.common.MongoDbTestFixture;
import com.utraveler.dao.entity.impl.PhotoEntityImpl;
import com.utraveler.dao.entity.impl.PhotoPushpinEntityImpl;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

public class PhotoDaoImplTest extends MongoDbTestFixture {

    private PhotoDao photoDao;
    private PhotoPushpinDao pushpinDao;


    @BeforeMethod
    public void setUp() {
        photoDao = new PhotoDaoImpl(mongoTemplate);
        pushpinDao = new PhotoPushpinDaoImpl(mongoTemplate);
    }


    @AfterMethod
    public void tearDown() {
        mongoTemplate.dropCollection(PhotoEntityImpl.class);
        mongoTemplate.dropCollection(PhotoPushpinEntityImpl.class);
    }


    @Test
    public void testInsertPhoto_EntityValid_InsertSuccess() {
        int photoId = 1;
        PhotoEntityImpl photoEntity = createPhotoEntity(photoId, 100);

        photoDao.insert(photoEntity);

        assertNotNull(photoDao.getById(photoId));
    }


    @Test
    public void testUpdatePhoto_EntityValid_UpdateSuccess() {
        int photoId = 1;
        PhotoEntityImpl photoEntity = createPhotoEntity(photoId, 100);

        photoDao.insert(photoEntity);
        photoEntity.setName("New Photo Name");

        photoDao.update(photoEntity);

        assertEquals(photoEntity.getName(), photoDao.getById(photoId).getName());
    }


    @Test
    public void testDeletePhoto_EntityValid_DeleteSuccess() {
        int photoId = 1;
        PhotoEntityImpl photoEntity = createPhotoEntity(photoId, 100);

        photoDao.insert(photoEntity);
        photoDao.delete(photoDao.getById(photoEntity.getId()));

        assertNull(photoDao.getById(photoId));
    }


    @Test
    public void testGetPhotosOfEvent_HasPhotos_ReturnPhotos() {
        photoDao.insert(createPhotoEntity(1, 100));
        photoDao.insert(createPhotoEntity(2, 101));

        assertEquals(1, photoDao.getPhotosOfEvent(100).size());
    }


    @Test
    public void testGetPhotosOfPushpin_HasPhotos_ReturnPhotos() {
        PhotoEntityImpl photoEntity1 = createPhotoEntity(1, 100);
        PhotoEntityImpl photoEntity2 = createPhotoEntity(2, 101);
        photoDao.insert(photoEntity1);
        photoDao.insert(photoEntity2);

        PhotoPushpinEntityImpl pushpinEntity1 = new PhotoPushpinEntityImpl();
        pushpinEntity1.setId(1000);
        pushpinEntity1.setEventId(photoEntity1.getEventId());
        pushpinEntity1.addPhoto(photoEntity1.getId());
        pushpinDao.insert(pushpinEntity1);
        PhotoPushpinEntityImpl pushpinEntity2 = new PhotoPushpinEntityImpl();
        pushpinEntity2.setId(1001);
        pushpinEntity2.setEventId(photoEntity2.getEventId());
        pushpinEntity2.addPhoto(photoEntity2.getId());
        pushpinDao.insert(pushpinEntity2);
    }


    @Test
    public void testDeletePhotosOfEvent_HasPhotos_DeletePhotos() {
        photoDao.insert(createPhotoEntity(1, 100));
        photoDao.insert(createPhotoEntity(2, 101));

        photoDao.deleteFromEvent(100);
        assertEquals(1, photoDao.getPhotosOfEvent(101).size());

        photoDao.deleteFromEvent(101);
        assertEquals(0, photoDao.getPhotosOfEvent(100).size());
        assertEquals(0, photoDao.getPhotosOfEvent(101).size());
    }


    private PhotoEntityImpl createPhotoEntity(long id, long eventId) {
        PhotoEntityImpl photoEntity = new PhotoEntityImpl();
        photoEntity.setId(id);
        photoEntity.setEventId(eventId);
        photoEntity.setName("Photo1");
        return photoEntity;
    }
}
