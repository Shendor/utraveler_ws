package com.utraveler.dao.impl;

import java.util.Collection;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.utraveler.dao.common.MongoDbTestFixture;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.impl.EventEntityImpl;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

public class EventDaoImplTest extends MongoDbTestFixture {

    private static final int USER_ID = 2000;

    private EventDaoImpl eventDao;


    @BeforeMethod
    public void setUp() {
        eventDao = new EventDaoImpl(mongoTemplate);
    }


    @AfterMethod
    public void tearDown() {
        mongoTemplate.dropCollection(EventEntityImpl.class);
    }


    @Test
    public void testInsertEventEntity_EventSetCorrectly_EntityInserted() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);

        assertNotNull(eventDao.getById(eventEntity.getId()));
    }


    @Test
    public void testInsertEventsEntities_EventsSetCorrectly_EntitiesInserted() {
        Collection<EventEntity> events = Lists.newArrayList();
        int insertedEventsQuantity = 10;
        for (int i = 0; i < insertedEventsQuantity; i++) {
            EventEntityImpl eventEntity = createEventEntity();
            eventEntity.setId(i);
            events.add(eventEntity);
        }

        eventDao.insert(events);

        assertEquals(insertedEventsQuantity, eventDao.findEventsOfUser(USER_ID).size());
    }


    @Test
    public void testDeleteEventEntity_EventSetCorrectly_EntityDeleted() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);
        eventDao.delete(eventDao.getById(eventEntity.getId()));

        assertNull(eventDao.getById(eventEntity.getId()));
    }


    @Test
    public void testUpdateEventEntity_EventSetCorrectly_EntityUpdated() {
        String newName = "NewName";
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);
        eventEntity.setName(newName);
        eventDao.update(eventEntity);

        assertEquals(newName, eventDao.getById(eventEntity.getId()).getName());
    }


    @Test
    public void testGetEventByUserId_UserIdSet_CorrectEntityReturned() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);

        Collection<? extends EventEntity> eventsOfUser = eventDao.findEventsOfUser(eventEntity.getUserId());
        assertEquals(eventEntity.getName(), eventsOfUser.iterator().next().getName());
    }


    @Test
    public void testIsBelongToUser_UserHasEvent_ReturnTrue() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);

        assertTrue(eventDao.isBelongToUser(eventEntity.getId(), eventEntity.getUserId()));
        assertFalse(eventDao.isBelongToUser(eventEntity.getId(), 10));
    }


    private EventEntityImpl createEventEntity() {
        EventEntityImpl eventEntity = new EventEntityImpl();
        eventEntity.setId(1000);
        eventEntity.setUserId(USER_ID);
        eventEntity.setName("Event1");
        return eventEntity;
    }
}
