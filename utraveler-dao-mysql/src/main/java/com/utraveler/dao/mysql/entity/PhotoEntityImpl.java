package com.utraveler.dao.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.PhotoEntity;

@Entity(name = "photo")
@NamedQueries({
                      @NamedQuery(name = "Photo.findPhotosOfEvent",
                                  query = "SELECT p FROM photo AS p " +
                                          "WHERE p.event.id=:eventId"),

                      @NamedQuery(name = "Photo.getPhotosQuantity",
                                  query = "SELECT COUNT(p) FROM photo AS p " +
                                          "WHERE p.event.id=:eventId"),

                      @NamedQuery(name = "Photo.findPhotoOfEvent",
                                  query = "SELECT p FROM photo AS p " +
                                          "WHERE p.id=:id AND p.event.id=:eventId"),

                      @NamedQuery(name = "Photo.deleteFromEvent",
                                  query = "DELETE FROM photo AS p " +
                                          "WHERE p.event.id=:eventId"),

                      @NamedQuery(name = "Photo.deletePhotosFromEvent",
                                  query = "DELETE FROM photo AS p " +
                                          "WHERE p.event.id=:eventId AND p.id IN (:photosId)"),

                      @NamedQuery(name = "Photo.getPhotosQuantityOfUser",
                                  query = "SELECT Count(p) FROM photo AS p " +
                                          "WHERE p.event.user.id=:userId"),

              })
public class PhotoEntityImpl extends AbstractEntity implements PhotoEntity, Comparable<PhotoEntityImpl> {

    private String name;
    private DateTime date;
    private String imageUrl;
    private String description;
    private byte[] thumbnail;
    private int width;
    private int height;
    private double longitude;
    private double latitude;
    private String facebookPostId;
    private String facebookAlbumId;
    private String facebookPhotoId;
    private EventEntity event;


    @Override
    @Column(length = 255, nullable = false)
    public String getName() {
        return name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    @Column(name = "date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getDate() {
        return date;
    }


    @Override
    public void setDate(DateTime date) {
        this.date = date;
    }


    @Override
    @Column(length = 1024)
    public String getImageUrl() {
        return imageUrl;
    }


    @Override
    @Column(length = 4096)
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    @Lob
    @Column(name = "thumbnail", nullable = true, columnDefinition = "blob")
    public byte[] getThumbnail() {
        return thumbnail;
    }


    @Override
    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Override
    @Column(nullable = true)
    public int getWidth() {
        return width;
    }


    @Override
    public void setWidth(int width) {
        this.width = width;
    }


    @Override
    @Column(nullable = true)
    public int getHeight() {
        return height;
    }


    @Override
    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    @Column(nullable = true)
    public double getLongitude() {
        return longitude;
    }


    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    @Column(nullable = true)
    public double getLatitude() {
        return latitude;
    }


    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    @Override
    @Column(name = "facebook_post_id", length = 128, nullable = true)
    public String getFacebookPostId() {
        return facebookPostId;
    }


    @Override
    public void setFacebookPostId(String facebookPostId) {
        this.facebookPostId = facebookPostId;
    }


    @Override
    @Column(name = "facebook_album_id", length = 128, nullable = true)
    public String getFacebookAlbumId() {
        return facebookAlbumId;
    }


    @Override
    public void setFacebookAlbumId(String facebookAlbumId) {
        this.facebookAlbumId = facebookAlbumId;
    }


    @Override
    @Column(name = "facebook_photo_id", nullable = true)
    public String getFacebookPhotoId() {
        return facebookPhotoId;
    }


    @Override
    public void setFacebookPhotoId(String facebookPhotoId) {
        this.facebookPhotoId = facebookPhotoId;
    }


    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EventEntityImpl.class)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    public EventEntity getEvent() {
        return event;
    }


    @Override
    public void setEvent(EventEntity event) {
        this.event = event;
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

        return id == that.id && date.equals(that.date);

    }


    @Override
    public int hashCode() {
        int result = (int)(id ^ (id >>> 32));
        result = 31 * result + date.hashCode();
        return result;
    }


    @Override
    public int compareTo(PhotoEntityImpl photo) {
        return -date.compareTo(photo.getDate());
    }
}
