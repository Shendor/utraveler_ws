package com.utraveler.model;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Event extends BaseModel implements Serializable {

    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private byte[] image;


    public Event() {
        startDate = DateTime.now();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public DateTime getStartDate() {
        return startDate;
    }


    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }


    public DateTime getEndDate() {
        return endDate;
    }


    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }


    public byte[] getImage() {
        return image;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }

}
