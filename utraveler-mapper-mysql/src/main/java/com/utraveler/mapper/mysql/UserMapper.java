package com.utraveler.mapper.mysql;

import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.mysql.entity.UserEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.User;

public class UserMapper implements Mapper<User, UserEntity> {

    @Override
    public UserEntity mapModel(User model) {
        UserEntityImpl userEntity = new UserEntityImpl();
        userEntity.setId(model.getId());
        userEntity.setName(model.getName());
        userEntity.setEmail(model.getEmail());
        userEntity.setRegisterDate(model.getRegisterDate());
        userEntity.setPassword(model.getPassword());
        userEntity.setAvatar(model.getAvatar());
        userEntity.setCover(model.getCover());
        userEntity.setDescription(model.getDescription());
        userEntity.setChangeDate(model.getChangeDate());

        return userEntity;
    }


    @Override
    public User mapEntity(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setRegisterDate(entity.getRegisterDate());
        user.setPassword(entity.getPassword());
        user.setAvatar(entity.getAvatar());
        user.setCover(entity.getCover());
        user.setDescription(entity.getDescription());
        user.setChangeDate(entity.getChangeDate());

        return user;
    }
}
