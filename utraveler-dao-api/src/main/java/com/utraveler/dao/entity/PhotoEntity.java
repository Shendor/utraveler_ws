package com.utraveler.dao.entity;

import org.joda.time.DateTime;

public interface PhotoEntity extends BaseEntity {

    String getName();


    void setName(String name);


    DateTime getDate();


    void setDate(DateTime date);


    String getImageUrl();


    String getDescription();


    void setDescription(String description);


    void setImageUrl(String imageUrl);


    byte[] getThumbnail();


    void setThumbnail(byte[] thumbnail);


    int getWidth();


    void setWidth(int width);


    int getHeight();


    void setHeight(int height);


    double getLongitude();


    void setLongitude(double longitude);


    double getLatitude();


    void setLatitude(double latitude);


    String getFacebookPostId();


    void setFacebookPostId(String facebookPostId);


    String getFacebookAlbumId();


    void setFacebookAlbumId(String facebookAlbumId);


    String getFacebookPhotoId();


    void setFacebookPhotoId(String facebookPhotoId);


    EventEntity getEvent();


    void setEvent(EventEntity event);
}
