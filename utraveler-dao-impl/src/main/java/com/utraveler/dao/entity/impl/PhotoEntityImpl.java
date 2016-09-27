package com.utraveler.dao.entity.impl;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.utraveler.dao.entity.PhotoEntity;

@Document(collection = "photo")
public class PhotoEntityImpl implements PhotoEntity, Comparable<PhotoEntityImpl> {

    @Id
    private long id;
    private long eventId;
    private String name;
    private DateTime date;
    private String imageUrl;
    private byte[] thumbnail;


    @Override
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
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
    public String getName() {
        return name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public DateTime getDate() {
        return date;
    }


    @Override
    public void setDate(DateTime date) {
        this.date = date;
    }


    @Override
    public String getImageUrl() {
        return imageUrl;
    }


    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public byte[] getThumbnail() {
        return thumbnail;
    }


    @Override
    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhotoEntityImpl that = (PhotoEntityImpl)o;

        return eventId == that.eventId && id == that.id;

    }


    @Override
    public int hashCode() {
        int result = (int)(id ^ (id >>> 32));
        result = 31 * result + (int)(eventId ^ (eventId >>> 32));
        return result;
    }


    @Override
    public int compareTo(PhotoEntityImpl photo) {
        return getDate().compareTo(photo.getDate());
    }
}
