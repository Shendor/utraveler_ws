package com.utraveler.dao.mysql;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.utraveler.dao.EventDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.EventEntityImpl;

@Repository("eventDao")
public class EventDaoImpl extends BaseDao<EventEntity> implements EventDao {


    @Override
    public Collection<? extends EventEntity> findEventsOfUser(long userId) {
        Query query = getSession().getNamedQuery("Event.findEventsOfUser");
        query.setLong("userId", userId);
        return query.list();
    }


    @Override
    public EventEntity getById(long id) {
        return (EventEntity)getSession().get(EventEntityImpl.class, id);
    }


    @Override
    public boolean isBelongToUser(long id, long userId) {
        return findEventOfUser(id, userId) != null;
    }


    @Override
    public EventEntity findEventOfUser(long eventId, long userId) {
        Query query = getSession().getNamedQuery("Event.findEventOfUser");
        query.setLong("id", eventId);
        query.setLong("userId", userId);

        return (EventEntity)query.uniqueResult();
    }


    @Override
    public int getEventsQuantity(long userId) {
        Query query = getSession().getNamedQuery("Event.getEventQuantityOfUser");
        query.setLong("userId", userId);

        return ((Long)query.uniqueResult()).intValue();
    }
}
