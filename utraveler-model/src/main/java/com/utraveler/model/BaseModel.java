package com.utraveler.model;

import org.joda.time.DateTime;

public class BaseModel {

    private long id;
    private DateTime changeDate;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public DateTime getChangeDate() {
        return changeDate;
    }


    public void setChangeDate(DateTime changeDate) {
        this.changeDate = changeDate;
    }
}
