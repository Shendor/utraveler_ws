package com.utraveler.dao.mysql;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.PhotoEntity;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.mysql.common.TestPersistenceDao;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import com.utraveler.dao.mysql.entity.PhotoEntityImpl;
import com.utraveler.dao.mysql.entity.UserEntityImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.PHOTO_TABLE, UTravelerTestFixture.EVENT_TABLE, UTravelerTestFixture.USER_TABLE})
public class PhotoDaoImplTest extends UTravelerTestFixture {

    private PhotoDao photoDao;
    private TestPersistenceDao testPersistenceDao;
    private UserEntityImpl user;


    @Before
    public void setUp() {
        photoDao = context.getBean(PhotoDao.class);
        testPersistenceDao = context.getBean(TestPersistenceDao.class);
        user = testPersistenceDao.saveUser("User");
    }


    @Test
    public void testInsertPhoto() {
        PhotoEntity photo = savePhoto(user);

        assertNotNull(getById(photoDao, photo));
    }


    @Test
    public void testUpdate() {
        PhotoEntity photo = savePhoto(user);
        photo.setDate(new DateTime(2014, 1, 1, 0, 0));
        photoDao.update(photo);

        assertEquals(photo.getDate(), getById(photoDao, photo).getDate());
    }


    @Test
    public void testDeletePhotoFromEvent() {
        PhotoEntity photo = savePhoto(user);

        photoDao.deleteFromEvent(Lists.newArrayList(photo.getId()), photo.getEvent().getId());

        assertNull(getById(photoDao, photo));
    }


    @Test
    public void testDeleteAllPhotosFromEvent() {
        PhotoEntity photo = savePhoto(user);

        photoDao.deleteFromEvent(photo.getEvent().getId());

        assertNull(getById(photoDao, photo));
    }


    @Test
    public void testFindPhotosOfEvent_PhotosFound() {
        PhotoEntity photo = savePhoto(user);

        assertEquals(1, photoDao.findPhotosOfEvent(photo.getEvent().getId()).size());
        assertEquals(1, photoDao.getPhotosQuantity(photo.getEvent().getId()));
    }


    @Test
    public void testGetPhotosQuantityOfUser() {
        savePhoto(user);
        savePhoto(user);

        UserEntityImpl user2 = testPersistenceDao.saveUser("User2");
        savePhoto(user2);

        assertEquals(2, photoDao.getPhotosQuantityOfUser(user.getId()));
    }


    private PhotoEntity savePhoto(UserEntity user) {
        EventEntity event = testPersistenceDao.saveEvent(user);
        PhotoEntityImpl photo = new PhotoEntityImpl();
        photo.setImageUrl("url");
        photo.setName("name");
        photo.setDate(DateTime.now());
        photo.setEvent(event);
        photo.setThumbnail(new byte[]{1, 2, 3});

        photoDao.insert(photo);

        return photo;
    }
}
