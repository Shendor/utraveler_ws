package com.utraveler.ws.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.utraveler.auth.AuthenticationService;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.PhotoEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Photo;
import com.utraveler.model.User;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.PhotoWebService;

public class PhotoWebServiceImpl extends BaseWebService implements PhotoWebService {

    private static final Logger LOGGER = Logger.getLogger(PhotoWebServiceImpl.class);

    private PhotoDao photoDao;
    private EventDao eventDao;
    private AuthenticationService authenticationService;
    private Mapper<Photo, PhotoEntity> photoMapper;


    public void setPhotoDao(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }


    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    public void setPhotoMapper(Mapper<Photo, PhotoEntity> photoMapper) {
        this.photoMapper = photoMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getPhotosOfEvent(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Photos");
                if (isEventBelongToUser(eventId)) {
                    Collection<Photo> photos = Lists.newArrayList();
                    for (PhotoEntity photoEntity : photoDao.findPhotosOfEvent(eventId)) {
                        photos.add(photoMapper.mapEntity(photoEntity));
                    }
                    LOGGER.info("Operation executed successfully: Get Photos");
                    return photos;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to view Photos from this Event");
            }
        });
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getPhotosQuantityOfUser() {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Photos Quantity of User");
                User currentUser = getCurrentUser();
                if (currentUser != null) {
                    int photosQuantityOfUser = photoDao.getPhotosQuantityOfUser(currentUser.getId());
                    LOGGER.info("Operation executed successfully: Get Photos Quantity of User");
                    return photosQuantityOfUser;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to get Photos quantity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse updatePhoto(final Photo photo, final long photoId, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Update Photo");
                if (isEventBelongToUser(eventId)) {
                    validateRequest(photo);
                    findAndUpdatePhoto(photo, photoId, eventId);
                    return true;
                } else {
                    throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                           "User does not have permission to update Photo from this Event");
                }
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse updatePhotos(final List<Photo> photos, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Update Photos");
                if (isEventBelongToUser(eventId)) {
                    for (Photo photo : photos) {
                        validateRequest(photo);
                        findAndUpdatePhoto(photo, photo.getId(), eventId);
                    }
                    return true;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to update Photo from this Event");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse addPhotoToEvent(final Photo photo, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Photo");
                EventEntity event = getEventOfUser(eventId);
                int photosLimit = getCurrentUser().getSetting().getPhotosLimit();
                long photosQuantity = photoDao.getPhotosQuantity(eventId);
                if (event != null && isExceededLimit(photosLimit, photosQuantity + 1)) {
                    PhotoEntity photoEntity = validateAndCreatePhotoEntity(event, photo);
                    photoDao.insert(photoEntity);
                    LOGGER.info("Operation executed successfully: Add Photo");
                    return photoEntity.getId();
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to add Photo to this Event");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse addPhotosToEvent(final List<Photo> photos, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Photos");
                EventEntity event = getEventOfUser(eventId);
                int photosLimit = getCurrentUser().getSetting().getPhotosLimit();
                long photosQuantity = photoDao.getPhotosQuantity(eventId);
                if (event != null && isExceededLimit(photosLimit, photosQuantity + photos.size())) {
                    Collection<PhotoEntity> photoEntities = Lists.newArrayList();
                    for (Photo photo : photos) {
                        photoEntities.add(validateAndCreatePhotoEntity(event, photo));
                    }
                    photoDao.insert(photoEntities);
                    LOGGER.info("Operation executed successfully: Add Photos");
                    return getInsertedIds(photoEntities);
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to add Photo to this Event");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse deletePhotoFromEvent(long photoId, long eventId) {
        return deletePhotosFromEvent(Lists.newArrayList(photoId), eventId);
    }


    @Override
    @Transactional
    public JsonResponse deletePhotosFromEvent(final Collection<Long> photosId, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Delete Photos");
                if (isEventBelongToUser(eventId)) {
                    boolean isDeleted = photoDao.deleteFromEvent(photosId, eventId);
                    LOGGER.info("Operation executed successfully: Delete Photos");
                    return isDeleted;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to delete Photos from this Event");
            }
        });
    }


    private void findAndUpdatePhoto(Photo photo, long photoId, long eventId) {
        PhotoEntity photoEntity = photoDao.findPhotoOfEvent(photoId, eventId);
        if (photoEntity != null) {
            photoEntity.setDate(photo.getDate());
            photoEntity.setImageUrl(photo.getImageUrl());
            photoEntity.setDescription(photo.getDescription());
            photoEntity.setLatitude(photo.getCoordinate().getLat());
            photoEntity.setLongitude(photo.getCoordinate().getLng());
            photoEntity.setFacebookPhotoId(photo.getFacebookPhotoId());
            photoEntity.setFacebookPostId(photo.getFacebookPostId());
            photoEntity.setFacebookAlbumId(photo.getFacebookAlbumId());
            photoDao.update(photoEntity);

            LOGGER.info("Operation executed successfully: Update Photo");
        }
    }


    private PhotoEntity validateAndCreatePhotoEntity(EventEntity event, Photo photo) {
        validateRequest(photo);
        PhotoEntity photoEntity = photoMapper.mapModel(photo);
        photoEntity.setEvent(event);
        return photoEntity;
    }


    private boolean isEventBelongToUser(long eventId) {
        User currentUser = getCurrentUser();
        return currentUser != null && eventDao.isBelongToUser(eventId, currentUser.getId());
    }


    private EventEntity getEventOfUser(long eventId) {
        User currentUser = getCurrentUser();
        return eventDao.findEventOfUser(eventId, currentUser.getId());
    }


    private User getCurrentUser() {
        return authenticationService.getCurrentUser();
    }
}
