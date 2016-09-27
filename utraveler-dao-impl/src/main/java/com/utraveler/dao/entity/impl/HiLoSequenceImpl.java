package com.utraveler.dao.entity.impl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class HiLoSequenceImpl {

    @Id
    private String tableName;
    private long id;


    public HiLoSequenceImpl() {
    }


    public HiLoSequenceImpl(String tableName) {
        this.tableName = tableName;
    }


    public String getTableName() {
        return tableName;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }
}
