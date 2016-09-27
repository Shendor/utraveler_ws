package com.utraveler.mapper.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.dao.entity.impl.RouteEntityImpl;
import com.utraveler.dao.entity.impl.RoutePushpinEntityImpl;
import com.utraveler.model.Color;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.Route;
import com.utraveler.model.RoutePushpin;
import static org.testng.Assert.assertEquals;

public class RouteMapperTest {


    private RouteMapper routeMapper;


    @BeforeMethod
    public void setUp()
            throws Exception {
        routeMapper = new RouteMapper();
        routeMapper.setRoutePushpinMapper(new RoutePushpinMapper());
    }


    @Test
    public void testMapEntity_AllFieldsProvided_MappingSuccess()
            throws Exception {

        RouteEntityImpl routeEntity = new RouteEntityImpl();
        routeEntity.setId(100);
        routeEntity.setName("Route1");
        routeEntity.setDescription("Description");
        routeEntity.setCoordinates("1, 2, 3"); // ignore the last coordinate
        routeEntity.setColor(new byte[]{1, 2, 3});
        routeEntity.addPushpin(createPushpinEntity());

        Route route = routeMapper.mapEntity(routeEntity);

        assertEquals(route.getId(), routeEntity.getId());
        assertEquals(route.getName(), routeEntity.getName());
        assertEquals(route.getDescription(), routeEntity.getDescription());
        assertEquals(route.getColor().getR(), routeEntity.getColor()[0]);
        assertEquals(route.getColor().getG(), routeEntity.getColor()[1]);
        assertEquals(route.getColor().getB(), routeEntity.getColor()[2]);
        assertEquals(route.getCoordinates().size(), 1);
        assertEquals(route.getPushpins().size(), routeEntity.getPushpins().size());

        route.getCoordinates().forEach((item) -> {
            assertEquals(item.getLatitude(), 1d);
            assertEquals(item.getLongitude(), 2d);
        });
    }


    @Test
    public void testMapModel_AllFieldsProvided_MappingSuccess()
            throws Exception {

        Route route = new Route();
        route.setId(100);
        route.setName("Route1");
        route.setDescription("Description");
        route.setCoordinates(Lists.newArrayList(new GeoCoordinate(1, 2)));
        route.setColor(new Color((byte)1, (byte)2, (byte)3));
        route.setPushpins(Lists.newArrayList(createPushpin()));

        RouteEntity routeEntity = routeMapper.mapModel(route);

        assertEquals(routeEntity.getId(), route.getId());
        assertEquals(route.getName(), routeEntity.getName());
        assertEquals(route.getDescription(), routeEntity.getDescription());
        assertEquals(routeEntity.getColor()[0], route.getColor().getR());
        assertEquals(routeEntity.getColor()[1], route.getColor().getG());
        assertEquals(routeEntity.getColor()[2], route.getColor().getB());
//        assertEquals(routeEntity.getCoordinates().length, 2);
//        assertEquals(routeEntity.getCoordinates()[0], 1d);
//        assertEquals(routeEntity.getCoordinates()[1], 2d);
        assertEquals(((RouteEntityImpl)routeEntity).getPushpins().size(), route.getPushpins().size());
    }


    private RoutePushpin createPushpin() {
        RoutePushpin routePushpin = new RoutePushpin();
        routePushpin.setCoordinate(new GeoCoordinate(10, 20));
        return routePushpin;
    }


    private RoutePushpinEntityImpl createPushpinEntity() {
        RoutePushpinEntityImpl routePushpinEntity = new RoutePushpinEntityImpl();
        routePushpinEntity.setLatitude(10);
        routePushpinEntity.setLongitude(20);

        return routePushpinEntity;
    }
}
