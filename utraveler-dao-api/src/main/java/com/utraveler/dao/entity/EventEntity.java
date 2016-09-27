package com.utraveler.dao.entity;

import org.joda.time.DateTime;

public interface EventEntity extends BaseEntity {


    UserEntity getUser();


    void setUser(UserEntity user);


    String getName();


    void setName(String name);


    DateTime getStartDate();


    void setStartDate(DateTime startDate);


    DateTime getEndDate();


    void setEndDate(DateTime endDate);


    byte[] getImage();


    void setImage(byte[] image);
}
