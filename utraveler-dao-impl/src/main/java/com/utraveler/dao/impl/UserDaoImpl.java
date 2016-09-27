package com.utraveler.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.utraveler.dao.UserDao;
import com.utraveler.dao.common.impl.MongoDbBaseDao;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.entity.impl.UserEntityImpl;

public class UserDaoImpl extends MongoDbBaseDao<UserEntity> implements UserDao {

    public UserDaoImpl(MongoOperations mongoOperations) {
        super(mongoOperations);
    }


    @Override
    protected String getTableName() {
        return "user";
    }


    @Override
    public UserEntity getById(long id) {
        return mongoOperations.findById(id, UserEntityImpl.class);
    }


    @Override
    public void insert(UserEntity entity) {
        super.insert(entity);
        if (entity.getUserName() == null) {
            entity.setUserName("User" + entity.getId());
            update(entity);
        }
    }


    @Override
    public UserEntity findUserByUserName(String userName) {
        Query query = Query.query(Criteria.where("userName").is(userName));
        List<UserEntityImpl> foundUsers = mongoOperations.find(query, UserEntityImpl.class);
        return foundUsers.size() == 1 ? foundUsers.get(0) : null;
    }


    @Override
    public UserEntity findUserByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        List<UserEntityImpl> foundUsers = mongoOperations.find(query, UserEntityImpl.class);
        return foundUsers.size() == 1 ? foundUsers.get(0) : null;
    }


    @Override
    public boolean isEmailExist(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return mongoOperations.count(query, UserEntityImpl.class) > 0;
    }
}
