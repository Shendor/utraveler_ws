package com.utraveler.dao.mysql.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.TripPlanEntity;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity(name = "trip_plan")
@NamedQueries({
        @NamedQuery(name = "TripPlan.findTripPlanOfEvent",
                query = "SELECT tp FROM trip_plan AS tp " +
                        "WHERE tp.event.id=:eventId"),

        @NamedQuery(name = "TripPlan.deleteFromEvent",
                query = "DELETE FROM trip_plan AS tp " +
                        "WHERE tp.event.id=:eventId")
})
public class TripPlanEntityImpl extends AbstractEntity implements TripPlanEntity {

    private String planItemsJson;
    private String flightPlanItemsJson;
    private String rentPlanItemsJson;
    private EventEntity event;


    @Basic
    @Override
    @Column(name = "plan_items_json", columnDefinition = "mediumtext", nullable = true)
    public String getPlanItemsJson() {
        return planItemsJson;
    }


    @Override
    public void setPlanItemsJson(String planItemsJson) {
        this.planItemsJson = planItemsJson;
    }


    @Basic
    @Override
    @Column(name = "flight_plan_items_json", columnDefinition = "mediumtext", nullable = true)
    public String getFlightPlanItemsJson() {
        return flightPlanItemsJson;
    }


    @Override
    public void setFlightPlanItemsJson(String flightPlanItemsJson) {
        this.flightPlanItemsJson = flightPlanItemsJson;
    }


    @Basic
    @Override
    @Column(name = "rent_plan_items_json", columnDefinition = "mediumtext", nullable = true)
    public String getRentPlanItemsJson() {
        return rentPlanItemsJson;
    }


    @Override
    public void setRentPlanItemsJson(String rentPlanItemsJson) {
        this.rentPlanItemsJson = rentPlanItemsJson;
    }


    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EventEntityImpl.class)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    public EventEntity getEvent() {
        return event;
    }


    @Override
    public void setEvent(EventEntity event) {
        this.event = event;
    }
}
