package com.utraveler.dao.mysql.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.RouteEntity;

@Entity(name = "route")
@NamedQueries({
                      @NamedQuery(name = "Route.findRoutesOfEvent",
                                  query = "SELECT r FROM route AS r " +
                                          "WHERE r.event.id=:eventId"),

                      @NamedQuery(name = "Route.getRoutesQuantity",
                                  query = "SELECT COUNT(r) FROM route AS r " +
                                          "WHERE r.event.id=:eventId"),

                      @NamedQuery(name = "Route.findRouteOfEvent",
                                  query = "SELECT r FROM route AS r " +
                                          "WHERE r.id=:id AND r.event.id=:eventId"),

                      @NamedQuery(name = "Route.deleteFromEvent",
                                  query = "DELETE FROM route AS r " +
                                          "WHERE r.event.id=:eventId"),

                      @NamedQuery(name = "Route.deleteRoutesFromEvent",
                                  query = "DELETE FROM route AS r " +
                                          "WHERE r.event.id=:eventId AND r.id IN (:routesId)"),

              })
public class RouteEntityImpl extends AbstractEntity implements RouteEntity {

    private String name;
    private String description;
    private String color;
    private String coordinatesJson;
    private String pushpinsJson;
    private String polygonsJson;
    private EventEntity event;


    @Override
    @Column(length = 255)
    public String getName() {
        return name;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    @Column(length = 4096)
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    @Column
    public String getColor() {
        return color;
    }


    @Override
    public void setColor(String color) {
        this.color = color;
    }


    @Override
    @Basic
    @Column(name = "coordinates_json", columnDefinition = "mediumtext", nullable = true)
    public String getCoordinatesJson() {
        return coordinatesJson;
    }


    @Override
    public void setCoordinatesJson(String coordinates) {
        this.coordinatesJson = coordinates;
    }


    @Basic
    @Override
    @Column(name = "pushpins_json", columnDefinition = "mediumtext", nullable = true)
    public String getPushpinsJson() {
        return pushpinsJson;
    }


    @Override
    public void setPushpinsJson(String pushpins) {
        this.pushpinsJson = pushpins;
    }


    @Override
    @Column(name = "polygons_json", nullable = true, columnDefinition = "mediumtext")
    public String getPolygonsJson() {
        return polygonsJson;
    }


    @Override
    public void setPolygonsJson(String polygonsJson) {
        this.polygonsJson = polygonsJson;
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
