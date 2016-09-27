package com.utraveler.dao.impl;

import java.util.Collection;
import java.util.Iterator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.utraveler.dao.RouteDao;
import com.utraveler.dao.common.MongoDbTestFixture;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.entity.impl.RouteEntityImpl;
import com.utraveler.dao.entity.impl.RoutePushpinEntityImpl;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertEquals;

public class RouteDaoImplTest extends MongoDbTestFixture {

    private RouteDao routeDao;


    @BeforeMethod
    public void setUp() {
        routeDao = new RouteDaoImpl(mongoTemplate);
    }


    @AfterMethod
    public void tearDown() {
        mongoTemplate.dropCollection(RouteEntityImpl.class);
    }


    @Test
    public void testInsertRoute_EntityValid_InsertSuccess() {
        RouteEntityImpl routeEntity = createRouteEntity(1, 100);
        routeDao.insert(routeEntity);

        assertNotNull(routeDao.getById(1));
    }


    @Test
    public void testGetById_EntityValid_AllFieldsIncluded() {
        RouteEntityImpl routeEntity = createRouteEntity(1, 100);
        routeDao.insert(routeEntity);

        RouteEntity foundEntity = routeDao.getById(1);
        assertNotNull(foundEntity);
//        assertEquals(3, routeEntity.getCoordinates().length);
        assertEquals(1, routeEntity.getPushpins().size());
    }


    @Test
    public void testUpdateRoute_EntityValid_UpdateSuccess() {
        RouteEntityImpl routeEntity = createRouteEntity(1, 100);
        routeDao.insert(routeEntity);
        routeEntity.setEventId(1000);
        routeDao.update(routeEntity);

        assertEquals(routeEntity.getEventId(), routeDao.getById(1).getEventId());
    }


    @Test
    public void testDeleteRoute_EntityValid_DeleteSuccess() {
        RouteEntityImpl routeEntity = createRouteEntity(1, 100);
        routeDao.insert(routeEntity);
        routeDao.delete(routeDao.getById(routeEntity.getId()));

        assertNull(routeDao.getById(1));
    }


    @Test
    public void testDeleteRouteFromEvent_EntityValid_DeleteSuccess() {
        int eventId = 100;
        RouteEntityImpl routeEntity = createRouteEntity(1, eventId);

        routeDao.insert(routeEntity);
        routeDao.deleteFromEvent(eventId);

        assertNull(routeDao.getById(routeEntity.getId()));
    }


    @Test
    public void testGetRoutesOfEvent_CoordinatesAndPushpinMustBeExcluded_Success() {
        int eventId = 100;
        routeDao.insert(createRouteEntity(1, eventId));

        Collection<? extends RouteEntity> routesOfEvent = routeDao.getRoutesOfEvent(eventId);
        Iterator<? extends RouteEntity> iterator = routesOfEvent.iterator();
        RouteEntityImpl routeEntity = (RouteEntityImpl)iterator.next();

        assertEquals(1, routesOfEvent.size());
        assertNull(routeEntity.getCoordinates());
        assertEquals(0, routeEntity.getPushpins().size());
    }


    private RouteEntityImpl createRouteEntity(int id, int eventId) {
        RouteEntityImpl routeEntity = new RouteEntityImpl();
        routeEntity.setId(id);
        routeEntity.setEventId(eventId);
        routeEntity.setCoordinates("1, 2, 3");
        routeEntity.addPushpin(new RoutePushpinEntityImpl());
        return routeEntity;
    }
}
