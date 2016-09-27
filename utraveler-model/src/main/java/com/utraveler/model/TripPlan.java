package com.utraveler.model;

public class TripPlan extends BaseModel {

    private String planItemsJson;
    private String flightPlanItemsJson;
    private String rentPlanItemsJson;


    public String getPlanItemsJson() {
        return planItemsJson;
    }


    public void setPlanItemsJson(String planItemsJson) {
        this.planItemsJson = planItemsJson;
    }


    public String getFlightPlanItemsJson() {
        return flightPlanItemsJson;
    }


    public void setFlightPlanItemsJson(String flightPlanItemsJson) {
        this.flightPlanItemsJson = flightPlanItemsJson;
    }


    public String getRentPlanItemsJson() {
        return rentPlanItemsJson;
    }


    public void setRentPlanItemsJson(String rentPlanItemsJson) {
        this.rentPlanItemsJson = rentPlanItemsJson;
    }
}
