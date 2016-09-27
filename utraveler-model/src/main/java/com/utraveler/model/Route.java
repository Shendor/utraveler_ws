package com.utraveler.model;

public class Route extends BaseModel {

    private String name;
    private String description;
    private String color;
    private String pushpinsJson;
    private String coordinatesJson;
    private String polygonsJson;


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


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getPushpinsJson() {
        return pushpinsJson;
    }


    public void setPushpinsJson(String pushpinsJson) {
        this.pushpinsJson = pushpinsJson;
    }


    public String getCoordinatesJson() {
        return coordinatesJson;
    }


    public void setCoordinatesJson(String coordinatesJson) {
        this.coordinatesJson = coordinatesJson;
    }


    public String getPolygonsJson() {
        return polygonsJson;
    }


    public void setPolygonsJson(String polygonsJson) {
        this.polygonsJson = polygonsJson;
    }
}