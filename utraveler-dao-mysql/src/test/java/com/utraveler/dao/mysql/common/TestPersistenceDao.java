package com.utraveler.dao.mysql.common;

import org.joda.time.DateTime;

import com.utraveler.dao.EventDao;
import com.utraveler.dao.UserDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.mysql.entity.EventEntityImpl;
import com.utraveler.dao.mysql.entity.UserEntityImpl;

public class TestPersistenceDao {

    private UserDao userDao;
    private EventDao eventDao;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }


    public UserEntityImpl saveUser(String username) {
        UserEntityImpl entity = new UserEntityImpl();
        entity.setPassword("pswd");
        entity.setEmail(username);
        entity.setRegisterDate(DateTime.now());
        userDao.insert(entity);

        return entity;
    }


    public EventEntity saveEvent(UserEntity user) {
        EventEntityImpl eventEntity = new EventEntityImpl();
        eventEntity.setUser(user);
        eventEntity.setName("Event1");
        eventEntity.setStartDate(DateTime.now());

        eventDao.insert(eventEntity);
        return eventEntity;
    }

}
