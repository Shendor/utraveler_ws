package com.utraveler.mapper.impl;

import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.impl.EventEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Event;

public class EventMapper implements Mapper<Event, EventEntity> {

    @Override
    public EventEntity mapModel(Event model) {
        EventEntityImpl eventEntity = new EventEntityImpl();
        eventEntity.setId(model.getId());
        eventEntity.setName(model.getName());
        eventEntity.setStartDate(model.getStartDate());
        eventEntity.setEndDate(model.getEndDate());
        eventEntity.setImage(model.getImage());
        return eventEntity;
    }


    @Override
    public Event mapEntity(EventEntity entity) {
        Event event = new Event();
        event.setId(entity.getId());
        event.setName(entity.getName());
        event.setStartDate(entity.getStartDate());
        event.setEndDate(entity.getEndDate());
        event.setImage(entity.getImage());

        return event;
    }
}
