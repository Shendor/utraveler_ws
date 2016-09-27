package com.utraveler.mapper.impl;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import com.utraveler.dao.entity.PhotoEntity;
import com.utraveler.dao.entity.impl.PhotoEntityImpl;
import com.utraveler.model.Photo;
import static org.testng.Assert.assertEquals;

public class PhotoMapperTest {

    @Test
    public void testMapEntity_AllFieldsProvided_MappingSuccess()
            throws Exception {
        PhotoMapper photoMapper = new PhotoMapper();

        PhotoEntityImpl photoEntity = new PhotoEntityImpl();
        photoEntity.setId(100);
        photoEntity.setName("Photo1");
        photoEntity.setThumbnail(new byte[]{1, 2, 3});
        photoEntity.setDate(DateTime.now());
        photoEntity.setImageUrl("url");

        Photo photo = photoMapper.mapEntity(photoEntity);

        assertEquals(photo.getId(), photoEntity.getId());
        assertEquals(photo.getName(), photoEntity.getName());
        assertEquals(photo.getThumbnail(), photoEntity.getThumbnail());
        assertEquals(photo.getDate(), photoEntity.getDate());
        assertEquals(photo.getImageUrl(), photoEntity.getImageUrl());
    }


    @Test
    public void testMapModel_AllFieldsProvided_MappingSuccess()
            throws Exception {
        PhotoMapper photoMapper = new PhotoMapper();

        Photo photo = new Photo();
        photo.setId(100);
        photo.setName("Photo1");
        photo.setThumbnail(new byte[]{1, 2, 3});
        photo.setDate(DateTime.now());
        photo.setImageUrl("url");

        PhotoEntity photoEntity = photoMapper.mapModel(photo);

        assertEquals(photoEntity.getId(), photo.getId());
        assertEquals(photoEntity.getName(), photo.getName());
        assertEquals(photoEntity.getThumbnail(), photo.getThumbnail());
        assertEquals(photoEntity.getDate(), photo.getDate());
        assertEquals(photoEntity.getImageUrl(), photo.getImageUrl());
    }
}
