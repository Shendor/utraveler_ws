package com.utraveler.dao.entity.impl;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.utraveler.dao.entity.UserEntity;

@Document
public class UserEntityImpl implements UserEntity {

    @Id
    private long id;
    private String name;
    private String userName;
    private String password;
    private String email;
    private String avatarUrl;
    private DateTime registerDate;
    private String description;


    @Override
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getUserName() {
        return userName;
    }


    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String getEmail() {
        return email;
    }


    @Override
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }


    @Override
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    @Override
    public DateTime getRegisterDate() {
        return registerDate;
    }


    @Override
    public void setRegisterDate(DateTime registerDate) {
        this.registerDate = registerDate;
    }


    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
