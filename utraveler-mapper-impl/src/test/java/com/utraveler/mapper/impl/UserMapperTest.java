package com.utraveler.mapper.impl;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.entity.impl.UserEntityImpl;
import com.utraveler.model.User;
import static org.testng.Assert.assertEquals;

public class UserMapperTest {

    @Test
    public void testMapEntity_AllFieldsProvided_MappingSuccess()
            throws Exception {
        UserMapper userMapper = new UserMapper();

        UserEntityImpl userEntity = new UserEntityImpl();
        userEntity.setId(100);
        userEntity.setName("User1");
        userEntity.setUserName("Username1");
        userEntity.setRegisterDate(DateTime.now());
        userEntity.setPassword("Password");
        userEntity.setAvatarUrl("AvatarUrl");

        User user = userMapper.mapEntity(userEntity);

        assertEquals(user.getId(), userEntity.getId());
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getUserName(), userEntity.getUserName());
        assertEquals(user.getAvatarUrl(), userEntity.getAvatarUrl());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertEquals(user.getRegisterDate(), userEntity.getRegisterDate());
    }


    @Test
    public void testMapModel_AllFieldsProvided_MappingSuccess()
            throws Exception {
        UserMapper userMapper = new UserMapper();

        User user = new User();
        user.setId(100);
        user.setName("User1");
        user.setUserName("Username1");
        user.setRegisterDate(DateTime.now());
        user.setPassword("Password");
        user.setAvatarUrl("AvatarUrl");

        UserEntity userEntity = userMapper.mapModel(user);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getName(), user.getName());
        assertEquals(userEntity.getUserName(), user.getUserName());
        assertEquals(userEntity.getAvatarUrl(), user.getAvatarUrl());
        assertEquals(userEntity.getPassword(), user.getPassword());
        assertEquals(userEntity.getRegisterDate(), user.getRegisterDate());
    }
}
