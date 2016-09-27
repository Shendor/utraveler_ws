package com.utraveler.dao.mysql.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.utraveler.dao.entity.UserEntity;

@Entity(name = "user")
@NamedQueries({
                      @NamedQuery(name = "User.findUserByEmail",
                                  query = "SELECT u from user as u " +
                                          "WHERE u.email=:email"),

                      @NamedQuery(name = "User.isEmailExists",
                                  query = "SELECT COUNT(u) from user as u " +
                                          "WHERE u.email=:email"),
              })
public class UserEntityImpl extends AbstractEntity implements UserEntity {

    private String name;
    private String password;
    private String email;
    private byte[] avatar;
    private byte[] cover;
    private DateTime registerDate;
    private String description;
    private Collection<EventEntityImpl> events;


    @Override
    @Column(length = 255)
    public String getName() {
        return name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    @Column(length = 64)
    public String getPassword() {
        return password;
    }


    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    @Column(length = 255)
    public String getEmail() {
        return email;
    }


    @Override
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    @Lob
    @Column(name = "avatar", nullable = true, columnDefinition = "mediumblob")
    public byte[] getAvatar() {
        return avatar;
    }


    @Override
    public void setAvatar(byte[] avatarUrl) {
        this.avatar = avatarUrl;
    }


    @Override
    @Lob
    @Column(name = "cover", nullable = true, columnDefinition = "mediumblob")
    public byte[] getCover() {
        return cover;
    }


    @Override
    public void setCover(byte[] cover) {
        this.cover = cover;
    }


    @Override
    @Column(name = "register_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getRegisterDate() {
        return registerDate;
    }


    @Override
    public void setRegisterDate(DateTime registerDate) {
        this.registerDate = registerDate;
    }


    @Override
    @Column(length = 1024)
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Collection<EventEntityImpl> getEvents() {
        return events;
    }


    public void setEvents(Collection<EventEntityImpl> events) {
        this.events = events;
    }

}
