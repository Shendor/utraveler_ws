package com.utraveler.dao.entity.impl;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.entity.RoutePushpinEntity;

@Document(collection = "route")
public class RouteEntityImpl implements RouteEntity {

    @Id
    private long id;
    private long eventId;
    private String name;
    private String description;
    private byte[] color;
    private String coordinates;
    private Collection<RoutePushpinEntity> pushpins;


    public RouteEntityImpl() {
        pushpins = Lists.newArrayList();
    }


    @Override
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
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
    public long getEventId() {
        return eventId;
    }


    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }


    @Override
    public byte[] getColor() {
        return color;
    }


    @Override
    public void setColor(byte[] rgb) {
        if (rgb.length == 3) {
            this.color = rgb;
        }
    }


    @Override
    public String getCoordinates() {
        return coordinates;
    }


    @Override
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }


    public Collection<RoutePushpinEntity> getPushpins() {
        return pushpins;
    }


    public void addPushpin(RoutePushpinEntity pushpin) {
        this.pushpins.add(pushpin);
    }

}
