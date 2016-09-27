package com.utraveler.dao.entity;

public interface TripPlanEntity extends BaseEntity {

     String getPlanItemsJson();


     void setPlanItemsJson(String planItems);


     String getFlightPlanItemsJson();


     void setFlightPlanItemsJson(String flightPlanItems);


     String getRentPlanItemsJson();


     void setRentPlanItemsJson(String rentPlanItems);


     EventEntity getEvent();


     void setEvent(EventEntity event);
}
