package com.utraveler.mapper.impl;

import org.testng.annotations.Test;

import com.utraveler.dao.entity.PhotoPushpinEntity;
import com.utraveler.dao.entity.impl.PhotoPushpinEntityImpl;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.Pushpin;
import static org.testng.Assert.assertEquals;

public class PhotoPushpinMapperTest {

    @Test
    public void testMapEntity_AllFieldsProvided_MappingSuccess()
            throws Exception {
        PhotoPushpinMapper photoPushpinMapper = new PhotoPushpinMapper();

        PhotoPushpinEntityImpl pushpinEntity = new PhotoPushpinEntityImpl();
        pushpinEntity.setId(100);
        pushpinEntity.setLatitude(200);
        pushpinEntity.setLongitude(300);
        pushpinEntity.setThumbnail(new byte[]{1, 2, 3});

        Pushpin pushpin = photoPushpinMapper.mapEntity(pushpinEntity);

        assertEquals(pushpin.getId(), pushpinEntity.getId());
        assertEquals(pushpin.getCoordinate().getLatitude(), pushpinEntity.getLatitude());
        assertEquals(pushpin.getCoordinate().getLongitude(), pushpinEntity.getLongitude());
        assertEquals(pushpin.getThumbnail(), pushpinEntity.getThumbnail());
    }


    @Test
    public void testMapModel_AllFieldsProvided_MappingSuccess()
            throws Exception {
        PhotoPushpinMapper photoPushpinMapper = new PhotoPushpinMapper();

        Pushpin pushpin = new Pushpin();
        pushpin.setId(100);
        pushpin.setCoordinate(new GeoCoordinate(200, 300));
        pushpin.setThumbnail(new byte[]{1, 2, 3});

        PhotoPushpinEntity pushpinEntity = photoPushpinMapper.mapModel(pushpin);

        assertEquals(pushpinEntity.getId(), pushpinEntity.getId());
        assertEquals(pushpinEntity.getLatitude(), pushpinEntity.getLatitude());
        assertEquals(pushpinEntity.getLongitude(), pushpinEntity.getLongitude());
        assertEquals(pushpinEntity.getThumbnail(), pushpinEntity.getThumbnail());
    }
}
