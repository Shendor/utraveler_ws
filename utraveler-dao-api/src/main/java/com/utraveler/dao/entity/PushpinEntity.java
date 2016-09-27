package com.utraveler.dao.entity;

public interface PushpinEntity extends BaseEntity, LocationEntity {

    byte[] getThumbnail();


    void setThumbnail(byte[] thumbnail);
}
