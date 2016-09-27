package com.utraveler.model;

public class Pushpin {

    private long id;
    private byte[] thumbnail;
    private GeoCoordinate coordinate;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public byte[] getThumbnail() {
        return thumbnail;
    }


    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }


    public GeoCoordinate getCoordinate() {
        return coordinate;
    }


    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
    }

}
