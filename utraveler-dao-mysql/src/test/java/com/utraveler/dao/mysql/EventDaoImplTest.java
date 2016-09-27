package com.utraveler.dao.mysql;

import java.util.Collection;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.mysql.common.TestPersistenceDao;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import com.utraveler.dao.mysql.entity.EventEntityImpl;
import com.utraveler.dao.mysql.entity.UserEntityImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.EVENT_TABLE, UTravelerTestFixture.USER_TABLE})
public class EventDaoImplTest extends UTravelerTestFixture {

    private EventDao eventDao;
    private UserEntityImpl user;


    @Before
    public void setUp() {
        eventDao = context.getBean(EventDao.class);
        TestPersistenceDao testPersistenceDao = context.getBean(TestPersistenceDao.class);
        user = testPersistenceDao.saveUser("User");
    }


    @Test
    public void testInsertEventEntity_EventSetCorrectly_EntityInserted() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);

        assertNotNull(getById(eventDao, eventEntity));
    }


    @Test
    public void testGetEventByUserId_UserIdSet_CorrectEntityReturned() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);

        Collection<? extends EventEntity> eventsOfUser = eventDao.findEventsOfUser(eventEntity.getUser().getId());
        assertEquals(eventEntity.getName(), eventsOfUser.iterator().next().getName());
    }


    @Test
    public void testGetEventsQuantityByUserId_UserIdSet_CorrectQuantityReturned() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);

        int eventsQuantity = eventDao.getEventsQuantity(eventEntity.getUser().getId());
        assertEquals(1, eventsQuantity);
    }


    @Test
    public void testInsertEventsEntities_EventsSetCorrectly_EntitiesInserted() {
        Collection<EventEntity> events = Lists.newArrayList();
        int insertedEventsQuantity = 10;
        for (int i = 0; i < insertedEventsQuantity; i++) {
            events.add(createEventEntity());
        }

        eventDao.insert(events);

        assertEquals(insertedEventsQuantity, eventDao.findEventsOfUser(user.getId()).size());
    }


    @Test
    public void testDeleteEventEntity_EventSetCorrectly_EntityDeleted() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);
        eventDao.delete(eventDao.getById(eventEntity.getId()));

        assertNull(getById(eventDao, eventEntity));
    }


    @Test
    public void testUpdateEventEntity_EventSetCorrectly_EntityUpdated() {
        String newName = "NewName";
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);
        eventEntity.setName(newName);
        eventDao.update(eventEntity);

        assertEquals(newName, getById(eventDao, eventEntity).getName());
    }


    @Test
    public void testIsBelongToUser_UserHasEvent_ReturnTrue() {
        EventEntityImpl eventEntity = createEventEntity();

        eventDao.insert(eventEntity);

        assertTrue(eventDao.isBelongToUser(eventEntity.getId(), eventEntity.getUser().getId()));
        assertFalse(eventDao.isBelongToUser(eventEntity.getId(), 10));
    }


    private EventEntityImpl createEventEntity() {
        EventEntityImpl eventEntity = new EventEntityImpl();
        eventEntity.setUser(user);
        eventEntity.setName("Event1");
        eventEntity.setStartDate(DateTime.now());
        return eventEntity;
    }
}
