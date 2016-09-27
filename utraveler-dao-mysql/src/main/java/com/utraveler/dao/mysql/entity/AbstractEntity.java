package com.utraveler.dao.mysql.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.utraveler.dao.entity.BaseEntity;

@MappedSuperclass
public abstract class AbstractEntity implements BaseEntity {

    protected long id;
    protected DateTime changeDate;


    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
    }


    @Override
    @Column(name = "change_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getChangeDate() {
        return changeDate;
    }


    @Override
    public void setChangeDate(DateTime changeDate) {
        this.changeDate = changeDate;
    }
}
