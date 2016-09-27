package com.utraveler.mapper.impl;

import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.entity.impl.UserEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.User;

public class UserMapper implements Mapper<User, UserEntity> {

    @Override
    public UserEntity mapModel(User model) {
        UserEntityImpl userEntity = new UserEntityImpl();
        userEntity.setId(model.getId());
        userEntity.setName(model.getName());
        userEntity.setUserName(model.getUserName());
        userEntity.setEmail(model.getEmail());
        userEntity.setRegisterDate(model.getRegisterDate());
        userEntity.setPassword(model.getPassword());
        userEntity.setAvatarUrl(model.getAvatarUrl());
        userEntity.setDescription(model.getDescription());

        return userEntity;
    }


    @Override
    public User mapEntity(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setUserName(entity.getUserName());
        user.setEmail(entity.getEmail());
        user.setRegisterDate(entity.getRegisterDate());
        user.setPassword(entity.getPassword());
        user.setAvatarUrl(entity.getAvatarUrl());
        user.setDescription(entity.getDescription());

        return user;
    }
}
