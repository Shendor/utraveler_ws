package com.utraveler.dao.mysql;

import com.utraveler.dao.TripPlanDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.TripPlanEntity;
import com.utraveler.dao.mysql.common.TestPersistenceDao;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import com.utraveler.dao.mysql.entity.TripPlanEntityImpl;
import com.utraveler.dao.mysql.entity.UserEntityImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.TRIP_PLAN_TABLE, UTravelerTestFixture.EVENT_TABLE, UTravelerTestFixture.USER_TABLE})
public class TripPlanDaoImplTest extends UTravelerTestFixture {

    private TripPlanDao tripPlanDao;
    private TestPersistenceDao testPersistenceDao;
    private UserEntityImpl user;


    @Before
    public void setUp() {
        tripPlanDao = context.getBean(TripPlanDao.class);
        testPersistenceDao = context.getBean(TestPersistenceDao.class);
        user = testPersistenceDao.saveUser("User");
    }


    @Test
    public void testInsertTripPlan() {
        TripPlanEntity tripPlan = saveTripPlan();

        assertNotNull(tripPlanDao.findTripPlanOfEvent(tripPlan.getId()));
    }


    @Test
    public void testUpdate() {
        TripPlanEntity tripPlan = saveTripPlan();
        tripPlan.setPlanItemsJson("New plan items");
        tripPlanDao.update(tripPlan);

        assertEquals(tripPlan.getPlanItemsJson(), getById(tripPlanDao, tripPlan).getPlanItemsJson());
    }


    @Test
    public void testDeleteTripPlansFromEvent() {
        TripPlanEntity tripPlan = saveTripPlan();

        tripPlanDao.deleteFromEvent(tripPlan.getEvent().getId());

        assertNull(getById(tripPlanDao, tripPlan));
    }


    @Test
    public void testDeleteAllTripPlansFromEvent() {
        TripPlanEntity tripPlan = saveTripPlan();

        tripPlanDao.deleteFromEvent(tripPlan.getEvent().getId());

        assertNull(getById(tripPlanDao, tripPlan));
    }


    private TripPlanEntity saveTripPlan() {
        EventEntity event = testPersistenceDao.saveEvent(user);
        TripPlanEntity tripPlan = createTripPlan(event);
        tripPlanDao.insert(tripPlan);
        return tripPlan;
    }


    private TripPlanEntity createTripPlan(EventEntity event) {
        TripPlanEntityImpl tripPlan = new TripPlanEntityImpl();
        tripPlan.setPlanItemsJson("Plan items");
        tripPlan.setFlightPlanItemsJson("Flight items");
        tripPlan.setRentPlanItemsJson("Rent items");
        tripPlan.setEvent(event);
        return tripPlan;
    }
}
