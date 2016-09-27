package com.utraveler.dao.entity.impl;

import org.springframework.data.annotation.Id;

public abstract class BasePushpinEntity {

    @Id
    private long id;
    private double longitude;
    private double latitude;
    private byte[] thumbnail;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public byte[] getThumbnail() {
        return thumbnail;
    }


    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
