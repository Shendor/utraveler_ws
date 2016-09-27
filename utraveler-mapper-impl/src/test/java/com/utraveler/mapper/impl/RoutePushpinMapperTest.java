package com.utraveler.mapper.impl;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.utraveler.dao.entity.RoutePushpinEntity;
import com.utraveler.dao.entity.impl.RoutePushpinEntityImpl;
import com.utraveler.model.Color;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.RoutePushpin;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class RoutePushpinMapperTest {

    @Test
    public void testMapEntity_AllFieldsValid_Success()
            throws Exception {
        RoutePushpinMapper routePushpinMapper = new RoutePushpinMapper();
        RoutePushpinEntityImpl pushpinEntity = createRoutePushpinEntity();

        RoutePushpin routePushpin = routePushpinMapper.mapEntity(pushpinEntity);

        assertEquals(routePushpin.getId(), pushpinEntity.getId());
        assertEquals(routePushpin.getName(), pushpinEntity.getName());
        assertEquals(routePushpin.getDescription(), pushpinEntity.getDescription());
        assertEquals(routePushpin.getColor().getR(), pushpinEntity.getColor()[0]);
        assertEquals(routePushpin.getColor().getG(), pushpinEntity.getColor()[1]);
        assertEquals(routePushpin.getColor().getB(), pushpinEntity.getColor()[2]);
        assertTrue(Arrays.equals(routePushpin.getThumbnail(), pushpinEntity.getThumbnail()));
        assertEquals(routePushpin.getCoordinate().getLatitude(), pushpinEntity.getLatitude());
        assertEquals(routePushpin.getCoordinate().getLongitude(), pushpinEntity.getLongitude());
    }


    @Test
    public void testMapModel_AllFieldsValid_Success()
            throws Exception {
        RoutePushpinMapper routePushpinMapper = new RoutePushpinMapper();
        RoutePushpin routePushpin = createRoutePushpin();

        RoutePushpinEntity pushpinEntity = routePushpinMapper.mapModel(routePushpin);

        assertEquals(pushpinEntity.getId(), routePushpin.getId());
        assertEquals(pushpinEntity.getName(), routePushpin.getName());
        assertEquals(pushpinEntity.getDescription(), routePushpin.getDescription());
        assertEquals(pushpinEntity.getColor()[0], routePushpin.getColor().getR());
        assertEquals(pushpinEntity.getColor()[1], routePushpin.getColor().getG());
        assertEquals(pushpinEntity.getColor()[2], routePushpin.getColor().getB());
        assertTrue(Arrays.equals(pushpinEntity.getThumbnail(), routePushpin.getThumbnail()));
        assertEquals(pushpinEntity.getLatitude(), routePushpin.getCoordinate().getLatitude());
        assertEquals(pushpinEntity.getLongitude(), routePushpin.getCoordinate().getLongitude());
    }


    @Test
    public void testMapModel_NoCoordinates_NullResult()
            throws Exception {
        RoutePushpinMapper routePushpinMapper = new RoutePushpinMapper();
        RoutePushpin routePushpin = createRoutePushpin();
        routePushpin.setCoordinate(null);

        assertNull(routePushpinMapper.mapModel(routePushpin));
    }


    @Test
    public void testMapEntity_NoCoordinates_NullResult()
            throws Exception {
        RoutePushpinMapper routePushpinMapper = new RoutePushpinMapper();
        RoutePushpinEntityImpl pushpinEntity = createRoutePushpinEntity();
        pushpinEntity.setLatitude(0);
        pushpinEntity.setLongitude(0);

        assertNull(routePushpinMapper.mapEntity(pushpinEntity));
    }


    private RoutePushpinEntityImpl createRoutePushpinEntity() {
        RoutePushpinEntityImpl pushpinEntity = new RoutePushpinEntityImpl();
        pushpinEntity.setId(100);
        pushpinEntity.setName("N1");
        pushpinEntity.setDescription("D1");
        pushpinEntity.setLatitude(10);
        pushpinEntity.setLongitude(20);
        pushpinEntity.setColor(new byte[]{1, 2, 3});
        pushpinEntity.setThumbnail(new byte[]{20, 30, 40, 50});
        return pushpinEntity;
    }


    private RoutePushpin createRoutePushpin() {
        RoutePushpin routePushpin = new RoutePushpin();
        routePushpin.setId(100);
        routePushpin.setName("N1");
        routePushpin.setDescription("D1");
        routePushpin.setCoordinate(new GeoCoordinate(10, 20));
        routePushpin.setColor(new Color((byte)1, (byte)2, (byte)3));
        routePushpin.setThumbnail(new byte[]{20, 30, 40, 50});
        return routePushpin;
    }
}
