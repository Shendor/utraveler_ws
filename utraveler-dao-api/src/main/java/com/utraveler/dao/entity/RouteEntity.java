package com.utraveler.dao.entity;

public interface RouteEntity extends BaseEntity {

    String getName();


    void setName(String name);


    String getDescription();


    void setDescription(String description);


    String getPushpinsJson();


    void setPushpinsJson(String pushpinsJson);


    String getPolygonsJson();


    void setPolygonsJson(String polygonsJson);


    EventEntity getEvent();


    void setEvent(EventEntity event);


    String getColor();


    void setColor(String rgb);


    public String getCoordinatesJson();


    public void setCoordinatesJson(String coordinatesJson);
}
