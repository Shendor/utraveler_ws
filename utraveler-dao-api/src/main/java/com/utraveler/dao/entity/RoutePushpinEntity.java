package com.utraveler.dao.entity;

import java.io.Serializable;

public interface RoutePushpinEntity extends PushpinEntity, Serializable {


    String getName();


    void setName(String name);


    String getDescription();


    void setDescription(String description);


    String getColor();


    void setColor(String rgb);
}
