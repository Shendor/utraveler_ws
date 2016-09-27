package com.utraveler.mapper.mysql;

import com.utraveler.dao.entity.PhotoEntity;
import com.utraveler.dao.mysql.entity.PhotoEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.Photo;

public class PhotoMapper implements Mapper<Photo, PhotoEntity> {

    @Override
    public PhotoEntity mapModel(Photo model) {
        PhotoEntityImpl photoEntity = new PhotoEntityImpl();
        photoEntity.setId(model.getId());
        photoEntity.setName(model.getName());
        photoEntity.setThumbnail(model.getThumbnail());
        photoEntity.setDate(model.getDate());
        photoEntity.setDescription(model.getDescription());
        photoEntity.setWidth(model.getWidth());
        photoEntity.setHeight(model.getHeight());
        photoEntity.setImageUrl(model.getImageUrl());
        photoEntity.setLatitude(model.getCoordinate().getLat());
        photoEntity.setLongitude(model.getCoordinate().getLng());
        photoEntity.setFacebookPostId(model.getFacebookPostId());
        photoEntity.setFacebookAlbumId(model.getFacebookAlbumId());
        photoEntity.setFacebookPhotoId(model.getFacebookPhotoId());
        photoEntity.setChangeDate(model.getChangeDate());

        return photoEntity;
    }


    @Override
    public Photo mapEntity(PhotoEntity entity) {
        Photo photo = new Photo();
        photo.setId(entity.getId());
        photo.setName(entity.getName());
        photo.setThumbnail(entity.getThumbnail());
        photo.setDate(entity.getDate());
        photo.setWidth(entity.getWidth());
        photo.setHeight(entity.getHeight());
        photo.setDescription(entity.getDescription());
        photo.setImageUrl(entity.getImageUrl());
        photo.setCoordinate(new GeoCoordinate(entity.getLatitude(),entity.getLongitude()));
        photo.setFacebookPostId(entity.getFacebookPostId());
        photo.setFacebookAlbumId(entity.getFacebookAlbumId());
        photo.setFacebookPhotoId(entity.getFacebookPhotoId());
        photo.setChangeDate(entity.getChangeDate());

        return photo;
    }
}
