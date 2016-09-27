package com.utraveler.dao.mysql;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.utraveler.dao.RouteDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.mysql.common.TestPersistenceDao;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import com.utraveler.dao.mysql.entity.RouteEntityImpl;
import com.utraveler.dao.mysql.entity.UserEntityImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.ROUTE_TABLE, UTravelerTestFixture.EVENT_TABLE, UTravelerTestFixture.USER_TABLE})
public class RouteDaoImplTest extends UTravelerTestFixture {

    private RouteDao routeDao;
    private TestPersistenceDao testPersistenceDao;
    private UserEntityImpl user;


    @Before
    public void setUp() {
        routeDao = context.getBean(RouteDao.class);
        testPersistenceDao = context.getBean(TestPersistenceDao.class);
        user = testPersistenceDao.saveUser("User");
    }


    @Test
    public void testInsertRoute() {
        RouteEntity route = saveRoute();

        assertNotNull(getById(routeDao, route));
    }


    @Test
    public void testDeleteRouteFromEvent() {
        RouteEntity route = saveRoute();

        routeDao.deleteFromEvent(Lists.newArrayList(route.getId()), route.getEvent().getId());

        assertNull(getById(routeDao, route));
    }


    @Test
    public void testDeleteAllMessagesFromEvent() {
        RouteEntity route = saveRoute();

        routeDao.deleteFromEvent(route.getEvent().getId());

        assertNull(getById(routeDao, route));
    }


    @Test
    public void testFindRoutesOfEvent_RouteFound() {
        RouteEntity route = saveRoute();

        assertNotNull(routeDao.findRouteOfEvent(route.getId(), route.getEvent().getId()));
        assertEquals(1, routeDao.findRoutesOfEvent(route.getEvent().getId()).size());
        assertEquals(1, routeDao.getRoutesQuantity(route.getEvent().getId()));
    }


    private RouteEntity saveRoute() {
        EventEntity event = testPersistenceDao.saveEvent(user);
        RouteEntityImpl routeEntity = new RouteEntityImpl();
        routeEntity.setName("Route");
        routeEntity.setColor("1");
        routeEntity.setCoordinatesJson("{21, 22, 23}");
        routeEntity.setDescription("Description");
        routeEntity.setEvent(event);
        routeEntity.setPushpinsJson("{5, 1, 2}");

        routeDao.insert(routeEntity);
        return routeEntity;
    }
}