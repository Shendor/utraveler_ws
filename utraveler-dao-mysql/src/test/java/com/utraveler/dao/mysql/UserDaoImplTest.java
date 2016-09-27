package com.utraveler.dao.mysql;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Assert;

import com.utraveler.dao.UserDao;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.USER_TABLE})
public class UserDaoImplTest extends UTravelerTestFixture {


    private UserDao userDao;


    @Before
    public void setUp() {
        userDao = context.getBean(UserDao.class);
    }


    @Test
    public void testInsertUserEntity_UserSetCorrectly_UserInserted() {
        UserEntity userEntity = createUser("User");

        userDao.insert(userEntity);

        Assert.assertNotNull(userDao.getById(userEntity.getId()));
    }


    @Test
    public void testDeleteUserEntity_UserSetCorrectly_UserDeleted() {
        UserEntity userEntity = createUser("User");

        userDao.insert(userEntity);
        userDao.delete(userEntity);

        assertNull(userDao.getById(userEntity.getId()));
    }


    @Test
    public void testUpdateUserEntity_UserSetCorrectly_UserUpdated() {
        UserEntity userEntity = createUser("User");

        userDao.insert(userEntity);
        userEntity.setName("New Name");
        userDao.update(userEntity);

        assertEquals(userEntity.getName(), userDao.getById(userEntity.getId()).getName());
    }


    @Test
    public void testFindUserByUsernameAndEmail_UserFound() {
        UserEntity userEntity = createUser("User");

        userDao.insert(userEntity);

        assertNotNull(userDao.findUserByEmail(userEntity.getEmail()));
    }


    @Test
    public void testIsEmailExist_ReturnTrue() {
        UserEntity userEntity = createUser("User");

        userDao.insert(userEntity);

        assertTrue(userDao.isEmailExist(userEntity.getEmail()));
    }
}
