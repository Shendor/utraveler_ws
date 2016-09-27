package com.utraveler.dao.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.utraveler.dao.entity.AppInfoEntity;

@Entity(name = "app_info")
public class AppInfoEntityImpl extends AbstractEntity implements AppInfoEntity {

    private String version;


    @Override
    @Column(name = "version")
    public String getVersion() {
        return version;
    }


    @Override
    public void setVersion(String version) {
        this.version = version;
    }
}
