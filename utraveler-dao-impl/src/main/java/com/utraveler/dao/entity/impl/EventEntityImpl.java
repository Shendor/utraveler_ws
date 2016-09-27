package com.utraveler.dao.entity.impl;


import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.utraveler.dao.entity.EventEntity;

@Document(collection = "event")
public class EventEntityImpl implements EventEntity {

    @Id
    private long id;
    private long userId;
    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private byte[] image;


    @Override
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
    }


    @Override
    public long getUserId() {
        return userId;
    }


    @Override
    public void setUserId(long userId) {
        this.userId = userId;
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
    public DateTime getStartDate() {
        return startDate;
    }


    @Override
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }


    @Override
    public DateTime getEndDate() {
        return endDate;
    }


    @Override
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }


    @Override
    public byte[] getImage() {
        return image;
    }


    @Override
    public void setImage(byte[] image) {
        this.image = image;
    }
}
