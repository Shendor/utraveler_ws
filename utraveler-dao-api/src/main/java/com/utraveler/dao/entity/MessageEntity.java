package com.utraveler.dao.entity;

import org.joda.time.DateTime;

public interface MessageEntity extends BaseEntity, LocationEntity {

    String getFacebookPostId();


    void setFacebookPostId(String facebookPostId);


    EventEntity getEvent();


    void setEvent(EventEntity event);


    String getText();


    void setText(String message);


    DateTime getDate();


    void setDate(DateTime date);
}
