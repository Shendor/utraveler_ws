package com.utraveler.dao.entity;

public interface PhotoPushpinEntity extends PushpinEntity {

    EventEntity getEvent();


    void setEvent(EventEntity event);

}
