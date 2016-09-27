package com.utraveler.dao;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.TripPlanEntity;

public interface TripPlanDao  extends Dao<TripPlanEntity>, DeleteEventDao {

    TripPlanEntity findTripPlanOfEvent(long eventId);

}
