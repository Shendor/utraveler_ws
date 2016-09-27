package com.utraveler.model;

import java.util.Collection;

public class RoutePolygon {

    private String name;
    private String color;
    private float opacity;
    private Collection<GeoCoordinate> coordinates;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public float getOpacity() {
        return opacity;
    }


    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }


    public Collection<GeoCoordinate> getCoordinates() {
        return coordinates;
    }


    public void setCoordinates(Collection<GeoCoordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
