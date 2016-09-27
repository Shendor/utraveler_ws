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
import com.utraveler.dao.entity.UserEntity;

@Entity(name = "event")
@NamedQueries({
                      @NamedQuery(name = "Event.findEventsOfUser",
                                  query = "SELECT e FROM event AS e " +
                                          "WHERE e.user.id=:userId"),

                      @NamedQuery(name = "Event.findEventOfUser",
                                  query = "SELECT e FROM event AS e " +
                                          "WHERE e.id=:id AND e.user.id=:userId"),

                      @NamedQuery(name = "Event.getEventQuantityOfUser",
                                  query = "SELECT Count(e) FROM event AS e " +
                                          "WHERE e.user.id=:userId")
              })
public class EventEntityImpl extends AbstractEntity implements EventEntity {

    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private byte[] image;
    private UserEntity user;


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
    @Column(name = "start_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getStartDate() {
        return startDate;
    }


    @Override
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }


    @Override
    @Column(name = "end_date", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getEndDate() {
        return endDate;
    }


    @Override
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }


    @Override
    @Lob
    @Column(name = "image", nullable = true, columnDefinition = "mediumblob")
    public byte[] getImage() {
        return image;
    }


    @Override
    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntityImpl.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUser() {
        return user;
    }


    @Override
    public void setUser(UserEntity user) {
        this.user = user;
    }

}
