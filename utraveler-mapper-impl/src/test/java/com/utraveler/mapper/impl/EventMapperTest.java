package com.utraveler.mapper.impl;

import org.testng.annotations.Test;

import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.impl.EventEntityImpl;
import com.utraveler.model.Event;
import static org.testng.Assert.assertEquals;

public class EventMapperTest {

    @Test
    public void testMapEntity_AllFieldsProvided_MappingSuccess()
            throws Exception {
        EventMapper eventMapper = new EventMapper();

        EventEntityImpl entity = new EventEntityImpl();
        entity.setId(100);
        entity.setName("Event1");

        Event event = eventMapper.mapEntity(entity);

        assertEquals(event.getId(), entity.getId());
        assertEquals(event.getName(), entity.getName());
    }


    @Test
    public void testMapModel_AllFieldsProvided_MappingSuccess()
            throws Exception {
        EventMapper eventMapper = new EventMapper();

        Event event = new Event();
        event.setId(100);
        event.setName("Event1");

        EventEntity entity = eventMapper.mapModel(event);

        assertEquals(entity.getId(), event.getId());
        assertEquals(entity.getName(), event.getName());
    }
}
