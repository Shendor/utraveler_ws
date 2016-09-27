package com.utraveler.model;

import org.joda.time.DateTime;

public class Photo extends BaseModel {

    private String name;
    private DateTime date;
    private String imageUrl;
    private String description;
    private GeoCoordinate coordinate;
    private int width;
    private int height;
    private byte[] thumbnail;
    private String facebookPostId;
    private String facebookAlbumId;
    private String facebookPhotoId;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public DateTime getDate() {
        return date;
    }


    public void setDate(DateTime date) {
        this.date = date;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public GeoCoordinate getCoordinate() {
        return coordinate == null ? GeoCoordinate.NULL_COORDINATE : coordinate;
    }


    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
    }


    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public byte[] getThumbnail() {
        return thumbnail;
    }


    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getFacebookPostId() {
        return facebookPostId;
    }


    public void setFacebookPostId(String facebookPostId) {
        this.facebookPostId = facebookPostId;
    }


    public String getFacebookAlbumId() {
        return facebookAlbumId;
    }


    public void setFacebookAlbumId(String facebookAlbumId) {
        this.facebookAlbumId = facebookAlbumId;
    }


    public String getFacebookPhotoId() {
        return facebookPhotoId;
    }


    public void setFacebookPhotoId(String facebookPhotoId) {
        this.facebookPhotoId = facebookPhotoId;
    }
}
