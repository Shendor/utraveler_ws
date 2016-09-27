package com.utraveler.dao.entity.impl;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.utraveler.dao.entity.MessageEntity;

@Document(collection = "message")
public class MessageEntityImpl implements MessageEntity {

    @Id
    private long id;
    private long eventId;
    private String text;
    private double longitude;
    private double latitude;
    private DateTime date;


    @Override
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
    }


    @Override
    public double getLongitude() {
        return longitude;
    }


    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public double getLatitude() {
        return latitude;
    }


    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    @Override
    public long getEventId() {
        return eventId;
    }


    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }


    @Override
    public String getText() {
        return text;
    }


    @Override
    public void setText(String text) {
        this.text = text;
    }


    @Override
    public DateTime getDate() {
        return date;
    }


    @Override
    public void setDate(DateTime date) {
        this.date = date;
    }
}
