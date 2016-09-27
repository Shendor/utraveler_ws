package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.Photo;
import com.utraveler.validator.util.ValidationUtil;

public class PhotoValidator implements Validator {


    public static final int MAX_PHOTO_NAME = 1024;
    public static final int MAX_DESCRIPTION_LENGTH = 4096;
    public static final int MAX_URL_LENGTH = 1024;
    public static final int MAX_FACEBOOK_ID_LENGTH = 255;
    public static final int MAX_PHOTO_THUMBNAIL = 65534;


    @Override
    public boolean supports(Class<?> targetClass) {
        return Photo.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Photo photo = (Photo)target;
        if (!ValidationUtil.isTextInRange(photo.getName(), 0, MAX_PHOTO_NAME)) {
            errors.rejectValue("name", "error.validation.photo.name");
        }
        if (!ValidationUtil.isTextInRange(photo.getDescription(), 0, MAX_DESCRIPTION_LENGTH)) {
            errors.rejectValue("description", "error.validation.photo.description");
        }
        if (!ValidationUtil.isTextInRange(photo.getImageUrl(), 0, MAX_URL_LENGTH)) {
            errors.rejectValue("imageUrl", "error.validation.photo.imageUrl");
        }
        if (!ValidationUtil.isTextInRange(photo.getFacebookAlbumId(), 0, MAX_FACEBOOK_ID_LENGTH)) {
            errors.rejectValue("facebookAlbumId", "error.validation.photo.facebookAlbumId");
        }
        if (!ValidationUtil.isTextInRange(photo.getFacebookPostId(), 0, MAX_FACEBOOK_ID_LENGTH)) {
            errors.rejectValue("facebookPostId", "error.validation.photo.facebookPostId");
        }
        if (photo.getThumbnail() != null && photo.getThumbnail().length >= MAX_PHOTO_THUMBNAIL) {
            errors.rejectValue("thumbnail", "error.validation.photo.thumbnail");
        }
    }
}
