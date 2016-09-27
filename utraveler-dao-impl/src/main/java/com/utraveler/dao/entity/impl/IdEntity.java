package com.utraveler.dao.entity.impl;

import com.utraveler.dao.entity.BaseEntity;

public class IdEntity implements BaseEntity {

    private long id;


    @Override
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
    }
}
