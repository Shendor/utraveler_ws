package com.utraveler.dao;

import java.util.Collection;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.EventEntity;

public interface EventDao extends Dao<EventEntity> {

    Collection<? extends EventEntity> findEventsOfUser(long userId);


    boolean isBelongToUser(long id, long userId);


    EventEntity findEventOfUser(long eventId, long userId);


    int getEventsQuantity(long userId);
}
