package com.utraveler.dao;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.UserEntity;

public interface UserDao extends Dao<UserEntity> {

    UserEntity findUserByEmail(String email);


    boolean isEmailExist(String email);
}
