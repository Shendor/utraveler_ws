package com.utraveler.dao.entity;

public interface UserSettingEntity extends BaseEntity {


    String getMainColor();


    void setMainColor(String mainColor);


    String getBackgroundColor();


    void setBackgroundColor(String backgroundColor);


    String getTextColor();


    void setTextColor(String textColor);


    float getCoverOpacity();


    void setCoverOpacity(float coverOpacity);


    boolean isLandscapeCover();


    void setLandscapeCover(boolean isLandscapeCover);


    String getLimitCode();


    void setLimitCode(String limitCode);


    UserEntity getUser();


    void setUser(UserEntity user);
}
