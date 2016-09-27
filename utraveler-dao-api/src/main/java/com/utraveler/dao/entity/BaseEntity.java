package com.utraveler.dao.entity;

import org.joda.time.DateTime;

public interface BaseEntity {

    long getId();


    void setId(long id);


    DateTime getChangeDate();


    void setChangeDate(DateTime changeDate);

}
