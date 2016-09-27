package com.utraveler.mapper.impl;

import com.utraveler.dao.entity.PhotoEntity;
import com.utraveler.dao.entity.impl.PhotoEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Photo;

public class PhotoMapper implements Mapper<Photo, PhotoEntity> {

    @Override
    public PhotoEntity mapModel(Photo model) {
        PhotoEntityImpl photoEntity = new PhotoEntityImpl();
        photoEntity.setId(model.getId());
        photoEntity.setName(model.getName());
        photoEntity.setThumbnail(model.getThumbnail());
        photoEntity.setDate(model.getDate());
        photoEntity.setImageUrl(model.getImageUrl());

        return photoEntity;
    }


    @Override
    public Photo mapEntity(PhotoEntity entity) {
        Photo photo = new Photo();
        photo.setId(entity.getId());
        photo.setName(entity.getName());
        photo.setThumbnail(entity.getThumbnail());
        photo.setDate(entity.getDate());
        photo.setImageUrl(entity.getImageUrl());

        return photo;
    }
}
