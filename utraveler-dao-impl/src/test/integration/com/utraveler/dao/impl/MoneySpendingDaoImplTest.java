package com.utraveler.dao.impl;

import java.util.Collection;

import org.joda.time.DateTime;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.utraveler.dao.MoneySpendingDao;
import com.utraveler.dao.common.MongoDbTestFixture;
import com.utraveler.dao.entity.CurrencyType;
import com.utraveler.dao.entity.MoneySpendingCategory;
import com.utraveler.dao.entity.impl.MoneySpendingEntityImpl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class MoneySpendingDaoImplTest extends MongoDbTestFixture {

    private static final int EVENT_ID = 100;

    private MoneySpendingDao moneySpendingDao;


    @BeforeMethod
    public void setUp() {
        moneySpendingDao = new MoneySpendingDaoImpl(mongoTemplate);
    }


    @AfterMethod
    public void tearDown() {
        mongoTemplate.dropCollection(MoneySpendingEntityImpl.class);
    }


    @Test
    public void testInsertMoneySpendingEntity_MoneySpendingSetCorrectly_MoneySpendingInserted() {
        MoneySpendingEntityImpl moneySpendingEntity = createMoneySpendingEntity(1, EVENT_ID);

        moneySpendingDao.insert(moneySpendingEntity);

        assertNotNull(moneySpendingDao.getById(moneySpendingEntity.getId()));
    }


    @Test
    public void testDeleteMoneySpendingEntity_MoneySpendingSetCorrectly_MoneySpendingDeleted() {
        MoneySpendingEntityImpl moneySpendingEntity = createMoneySpendingEntity(1, EVENT_ID);

        moneySpendingDao.insert(moneySpendingEntity);
        moneySpendingDao.delete(moneySpendingDao.getById(moneySpendingEntity.getId()));

        assertNull(moneySpendingDao.getById(moneySpendingEntity.getId()));
    }


    @Test
    public void testDeleteMoneySpendingEntities_MoneySpendingSetCorrectly_EntitiesDeleted() {
        Collection<Long> eventsId = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            MoneySpendingEntityImpl moneySpendingEntity = createMoneySpendingEntity(i + 1, EVENT_ID);
            moneySpendingEntity.setId(i + 1);
            moneySpendingDao.insert(moneySpendingEntity);
            eventsId.add(moneySpendingEntity.getId());
        }

//        moneySpendingDao.delete(eventsId);

        AssertJUnit.assertEquals(0, moneySpendingDao.getMoneySpendingsForEvent(EVENT_ID).size());
    }


    @Test
    public void testUpdateMoneySpendingEntity_MoneySpendingSetCorrectly_MoneySpendingUpdated() {
        MoneySpendingEntityImpl moneySpendingEntity = createMoneySpendingEntity(1, EVENT_ID);

        moneySpendingDao.insert(moneySpendingEntity);
        moneySpendingEntity.setCurrency(CurrencyType.USDollar);
        moneySpendingDao.update(moneySpendingEntity);

        assertEquals(moneySpendingEntity.getCurrency(), moneySpendingDao.getById(moneySpendingEntity.getId()).getCurrency());
    }


    @Test
    public void testGetMoneySpendingsByEvent_MoneySpendingSetCorrectly_GetCorrectEntities() {
        int eventId = 100;
        MoneySpendingEntityImpl moneySpendingEntity = createMoneySpendingEntity(1, eventId);

        moneySpendingDao.insert(moneySpendingEntity);

        assertEquals(1, moneySpendingDao.getMoneySpendingsForEvent(eventId).size());
    }


    @Test
    public void testDeleteMoneySpendingEntityForEvent_MoneySpendingSetCorrectly_MoneySpendingDeleted() {
        int eventId = 100;
        MoneySpendingEntityImpl moneySpendingEntity = createMoneySpendingEntity(1, eventId);

        moneySpendingDao.insert(moneySpendingEntity);
        moneySpendingDao.deleteFromEvent(eventId);

        assertNull(moneySpendingDao.getById(moneySpendingEntity.getId()));
    }


    private MoneySpendingEntityImpl createMoneySpendingEntity(long id, long eventId) {
        MoneySpendingEntityImpl MoneySpendingEntity = new MoneySpendingEntityImpl();
        MoneySpendingEntity.setId(id);
        MoneySpendingEntity.setEventId(eventId);
        MoneySpendingEntity.setAmount(10f);
        MoneySpendingEntity.setMoneySpendingCategory(MoneySpendingCategory.Camping);
        MoneySpendingEntity.setCurrency(CurrencyType.Euro);
        MoneySpendingEntity.setDate(DateTime.now());
        return MoneySpendingEntity;
    }
}
