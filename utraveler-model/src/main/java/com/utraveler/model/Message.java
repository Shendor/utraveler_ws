package com.utraveler.model;

import org.joda.time.DateTime;

public class Message extends BaseModel {

    private String text;
    private GeoCoordinate coordinate;
    private DateTime date;
    private String facebookPostId;


    public Message() {
        date = DateTime.now();
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public GeoCoordinate getCoordinate() {
        return coordinate == null ? GeoCoordinate.NULL_COORDINATE : coordinate;
    }


    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
    }


    public DateTime getDate() {
        return date;
    }


    public void setDate(DateTime date) {
        this.date = date;
    }


    public String getFacebookPostId() {
        return facebookPostId;
    }


    public void setFacebookPostId(String facebookPostId) {
        this.facebookPostId = facebookPostId;
    }
}
