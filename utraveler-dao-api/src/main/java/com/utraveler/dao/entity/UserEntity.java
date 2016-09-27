package com.utraveler.dao.entity;

import org.joda.time.DateTime;

public interface UserEntity extends BaseEntity {

    public String getName();


    public void setName(String name);


    public String getPassword();


    public void setPassword(String password);


    String getEmail();


    void setEmail(String email);


    public byte[] getAvatar();


    public void setAvatar(byte[] avatar);


    public byte[] getCover();


    public void setCover(byte[] cover);


    DateTime getRegisterDate();


    void setRegisterDate(DateTime registerDate);


    String getDescription();


    void setDescription(String role);
}
