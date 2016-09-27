package com.utraveler.dao.entity;

public interface ResetPasswordRecord extends BaseEntity {

    String getCode();


    void setCode(String code);


    long getUserId();


    void setUserId(long userId);
}
