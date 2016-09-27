package com.utraveler.dao.impl;

import com.utraveler.dao.common.MongoDbTestFixture;
import com.utraveler.dao.entity.impl.EventEntityImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EventDaoImplMultiThreadingTest extends MongoDbTestFixture {


    private EventDaoImpl eventDao;


    @BeforeClass
    public void beforeClass() {
        eventDao = new EventDaoImpl(mongoTemplate);
        eventDao.setKeyGenerator(new HiLoKeyGeneratorImpl(mongoTemplate));
    }


    @AfterClass
    public void afterClass() {
        mongoTemplate.dropCollection(EventEntityImpl.class);
    }


    @Test(invocationCount = 20, threadPoolSize = 20)
    public void testInsertFromManyThreads_AllEntitiesValid_InsertSuccess() {
        EventEntityImpl entity = new EventEntityImpl();
        entity.setName("Event1");
        eventDao.insert(entity);
    }
}
