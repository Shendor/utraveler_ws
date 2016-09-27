package com.utraveler.dao.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.entity.UserSettingEntity;

@Entity(name = "user_setting")
@NamedQueries({
                      @NamedQuery(name = "UserSetting.getSettingForUser",
                                  readOnly = true,
                                  query = "SELECT us FROM user_setting AS us " +
                                          "WHERE us.user.id=:userId")
              })
public class UserSettingEntityImpl extends AbstractEntity implements UserSettingEntity {

    private String mainColor;
    private String backgroundColor;
    private String textColor;
    private String limitCode;
    private float coverOpacity;
    private boolean isLandscapeCover;
    private UserEntity user;


    @Override
    @Column(name = "main_color", length = 9)
    public String getMainColor() {
        return mainColor;
    }


    @Override
    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }


    @Override
    @Column(name = "background_color", length = 9)
    public String getBackgroundColor() {
        return backgroundColor;
    }


    @Override
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    @Override
    @Column(name = "text_color", length = 9)
    public String getTextColor() {
        return textColor;
    }


    @Override
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


    @Override
    @Column(name = "cover_opacity")
    public float getCoverOpacity() {
        return coverOpacity;
    }


    @Override
    public void setCoverOpacity(float coverOpacity) {
        this.coverOpacity = coverOpacity;
    }


    @Override
    @Column(name = "is_landscape_cover")
    public boolean isLandscapeCover() {
        return isLandscapeCover;
    }


    @Override
    public void setLandscapeCover(boolean isLandscapeCover) {
        this.isLandscapeCover = isLandscapeCover;
    }


    @Override
    @Column(name = "limit_code")
    public String getLimitCode() {
        return limitCode;
    }


    @Override
    public void setLimitCode(String limitCode) {
        this.limitCode = limitCode;
    }


    @OneToOne(fetch = FetchType.LAZY, targetEntity = UserEntityImpl.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Override
    public UserEntity getUser() {
        return user;
    }


    @Override
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
