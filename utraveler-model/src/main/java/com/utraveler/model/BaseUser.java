package com.utraveler.model;

import org.joda.time.DateTime;

public class BaseUser extends BaseModel {

    private String name;
    private String description;
    private byte[] avatar;
    private byte[] cover;
    private DateTime registerDate;
    private UserSetting setting;


    public BaseUser() {
        registerDate = DateTime.now();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public byte[] getAvatar() {
        return avatar;
    }


    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }


    public byte[] getCover() {
        return cover;
    }


    public void setCover(byte[] cover) {
        this.cover = cover;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public DateTime getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate(DateTime registerDate) {
        this.registerDate = registerDate;
    }


    public UserSetting getSetting() {
        return setting;
    }


    public void setSetting(UserSetting setting) {
        this.setting = setting;
    }
}
