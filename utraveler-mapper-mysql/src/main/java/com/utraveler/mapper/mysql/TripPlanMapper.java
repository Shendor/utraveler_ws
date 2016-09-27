package com.utraveler.mapper.mysql;

import com.utraveler.dao.entity.TripPlanEntity;
import com.utraveler.dao.mysql.entity.TripPlanEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.TripPlan;

public class TripPlanMapper implements Mapper<TripPlan, TripPlanEntity> {

    @Override
    public TripPlanEntity mapModel(TripPlan model) {
        TripPlanEntityImpl tripPlanEntity = new TripPlanEntityImpl();
        tripPlanEntity.setId(model.getId());
        tripPlanEntity.setPlanItemsJson(model.getPlanItemsJson());
        tripPlanEntity.setFlightPlanItemsJson(model.getFlightPlanItemsJson());
        tripPlanEntity.setRentPlanItemsJson(model.getRentPlanItemsJson());
        tripPlanEntity.setChangeDate(model.getChangeDate());

       return tripPlanEntity;
    }


    @Override
    public TripPlan mapEntity(TripPlanEntity entity) {
        TripPlan tripPlan = new TripPlan();
        tripPlan.setId(entity.getId());
        tripPlan.setPlanItemsJson(entity.getPlanItemsJson());
        tripPlan.setFlightPlanItemsJson(entity.getFlightPlanItemsJson());
        tripPlan.setRentPlanItemsJson(entity.getRentPlanItemsJson());
        tripPlan.setChangeDate(entity.getChangeDate());

        return tripPlan;
    }
}
