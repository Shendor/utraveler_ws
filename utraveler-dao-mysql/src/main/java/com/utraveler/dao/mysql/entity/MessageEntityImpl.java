package com.utraveler.dao.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.MessageEntity;

@Entity(name = "message")
@NamedQueries({
                      @NamedQuery(name = "Message.findMessagesOfEvent",
                                  query = "SELECT m FROM message AS m " +
                                          "WHERE m.event.id=:eventId"),

                      @NamedQuery(name = "Message.getMessagesQuantity",
                                  query = "SELECT COUNT(m) FROM message AS m " +
                                          "WHERE m.event.id=:eventId"),

                      @NamedQuery(name = "Message.findMessageOfEvent",
                                  query = "SELECT m FROM message AS m " +
                                          "WHERE m.id=:id AND m.event.id=:eventId"),

                      @NamedQuery(name = "Message.deleteFromEvent",
                                  query = "DELETE FROM message AS m " +
                                          "WHERE m.event.id=:eventId"),

                      @NamedQuery(name = "Message.deleteMessagesFromEvent",
                                  query = "DELETE FROM message AS m " +
                                          "WHERE m.event.id=:eventId AND m.id IN (:messagesId)"),

              })
public class MessageEntityImpl extends AbstractEntity implements MessageEntity, Comparable<MessageEntityImpl> {

    private String text;
    private double longitude;
    private double latitude;
    private DateTime date;
    private String facebookPostId;
    private EventEntity event;


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
    @Column(length = 4096, nullable = false)
    public String getText() {
        return text;
    }


    @Override
    public void setText(String text) {
        this.text = text;
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
    @Column(name = "facebook_post_id", length = 255, nullable = true)
    public String getFacebookPostId() {
        return facebookPostId;
    }


    @Override
    public void setFacebookPostId(String facebookPostId) {
        this.facebookPostId = facebookPostId;
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

        MessageEntityImpl that = (MessageEntityImpl)o;

        return id == that.id && date.equals(that.date);

    }


    @Override
    public int hashCode() {
        int result = (int)(id ^ (id >>> 32));
        result = 31 * result + date.hashCode();
        return result;
    }


    @Override
    public int compareTo(MessageEntityImpl entity) {
        return -date.compareTo(entity.getDate());
    }
}
