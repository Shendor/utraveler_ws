package com.utraveler.dao.entity.impl;

import org.springframework.data.mongodb.core.mapping.Document;

import com.utraveler.dao.entity.RoutePushpinEntity;

@Document(collection = "route_pushpin")
public class RoutePushpinEntityImpl extends BasePushpinEntity implements RoutePushpinEntity {

    private long routeId;
    private String name;
    private String description;
    private byte[] color;


    @Override
    public long getRouteId() {
        return routeId;
    }


    @Override
    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public byte[] getColor() {
        return color;
    }


    @Override
    public void setColor(byte[] rgb) {
        this.color = rgb;
    }
}
