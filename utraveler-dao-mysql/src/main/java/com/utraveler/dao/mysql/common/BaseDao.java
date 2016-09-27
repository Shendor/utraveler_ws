package com.utraveler.dao.mysql.common;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.BaseEntity;

public abstract class BaseDao<T extends BaseEntity> implements Dao<T> {

    protected SessionFactory sessionFactory;


    @PostConstruct
    private void init() {
        if (sessionFactory == null) {
            throw new RuntimeException("SessionFactory was not provided");
        }
    }


    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void insert(Collection<T> entities) {
        Session session = getSession();
        int counter = 0;
        for (T entity : entities) {
            entity.setChangeDate(DateTime.now());
            session.save(entity);
            if (counter++ % 10 == 0) {
                session.flush();
                session.clear();
            }
        }
        session.flush();
        session.clear();
    }


    @Override
    public void insert(T entity) {
        entity.setChangeDate(DateTime.now());
        getSession().save(entity);
    }


    @Override
    public void update(T entity) {
        entity.setChangeDate(DateTime.now());
        getSession().merge(entity);
    }


    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }


    @Override
    public void delete(long id) {
        throw new NotYetImplementedException("Cannot delete by id");
    }


    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
