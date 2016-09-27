package com.utraveler.dao.impl;

import org.joda.time.DateTime;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.utraveler.dao.UserDao;
import com.utraveler.dao.common.MongoDbTestFixture;
import com.utraveler.dao.entity.impl.UserEntityImpl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class UserDaoImplTest extends MongoDbTestFixture {

    private UserDao userDao;


    @BeforeMethod
    public void setUp() {
        userDao = new UserDaoImpl(mongoTemplate);
    }


    @AfterMethod
    public void tearDown() {
        mongoTemplate.dropCollection(UserEntityImpl.class);
    }


    @Test
    public void testInsertUserEntity_UserSetCorrectly_UserInserted() {
        long id = 1;
        UserEntityImpl userEntity = createUserEntity(id);

        userDao.insert(userEntity);

        assertNotNull(userDao.getById(userEntity.getId()));
    }


    @Test
    public void testDeleteUserEntity_UserSetCorrectly_UserDeleted() {
        long id = 1;
        UserEntityImpl userEntity = createUserEntity(id);

        userDao.insert(userEntity);
        userDao.delete(userDao.getById(userEntity.getId()));

        assertNull(userDao.getById(userEntity.getId()));
    }


    @Test
    public void testUpdateUserEntity_UserSetCorrectly_UserUpdated() {
        long id = 1;
        UserEntityImpl userEntity = createUserEntity(id);

        userDao.insert(userEntity);
        userEntity.setName("New Name");
        userDao.update(userEntity);

        assertEquals(userEntity.getName(), userDao.getById(userEntity.getId()).getName());
    }


    private UserEntityImpl createUserEntity(long id) {
        UserEntityImpl userEntity = new UserEntityImpl();
        userEntity.setId(id);
        userEntity.setName("User1");
        userEntity.setPassword("password");
        userEntity.setRegisterDate(DateTime.now());
        return userEntity;
    }
}
