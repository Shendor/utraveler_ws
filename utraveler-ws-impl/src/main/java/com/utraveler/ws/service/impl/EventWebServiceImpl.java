package com.utraveler.ws.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.utraveler.auth.AuthenticationService;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.MessageDao;
import com.utraveler.dao.MoneySpendingDao;
import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.RouteDao;
import com.utraveler.dao.UserDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Event;
import com.utraveler.model.User;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.EventWebService;

public class EventWebServiceImpl extends BaseWebService implements EventWebService {

    private static final Logger LOGGER = Logger.getLogger(EventWebServiceImpl.class);

    private EventDao eventDao;
    private UserDao userDao;
    private MoneySpendingDao moneySpendingDao;
    private MessageDao messageDao;
    private PhotoDao photoDao;
    private RouteDao routeDao;
    private AuthenticationService authenticationService;
    private Mapper<Event, EventEntity> eventMapper;


    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void setMoneySpendingDao(MoneySpendingDao moneySpendingDao) {
        this.moneySpendingDao = moneySpendingDao;
    }


    public void setPhotoDao(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }


    public void setRouteDao(RouteDao routeDao) {
        this.routeDao = routeDao;
    }


    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    public void setEventMapper(Mapper<Event, EventEntity> eventMapper) {
        this.eventMapper = eventMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getEvent(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Event by Id");
                Event event = null;
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null) {
                    EventEntity eventEntity = eventDao.findEventOfUser(eventId, currentUser.getId());
                    if (eventEntity != null) {
                        event = eventMapper.mapEntity(eventEntity);
                        LOGGER.info("Operation executed successfully: Get Event by Id");
                    }
                }
                return event;
            }
        });
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getEvents(final long userId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Events");
                List<Event> events = Lists.newArrayList();
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null && currentUser.getId() == userId) {
                    for (EventEntity eventEntity : eventDao.findEventsOfUser(userId)) {
                        events.add(eventMapper.mapEntity(eventEntity));
                    }
                    LOGGER.info("Operation executed successfully: Get Events");
                } else {
                    throw new UTravelerWebServiceException(ErrorCode.USER_NOT_AUTHENTICATED,
                                                           "The user was not authenticated or does not have permission to view events");
                }
                return events;
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse insertEvent(final Event event) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Event");
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null) {
                    int routesLimit = currentUser.getSetting().getEventsLimit();
                    long eventsQuantity = eventDao.getEventsQuantity(currentUser.getId());
                    if (isExceededLimit(routesLimit, eventsQuantity + 1)) {
                        UserEntity userEntity = userDao.getById(currentUser.getId());
                        EventEntity eventEntity = validateAndCreateEventEntity(userEntity, event);
                        eventDao.insert(eventEntity);
                        LOGGER.info("Operation executed successfully: Add Event");
                        return eventEntity.getId();
                    }
                    return false;
                } else {
                    throw new UTravelerWebServiceException(ErrorCode.USER_NOT_AUTHENTICATED,
                                                           "The user was not authenticated to add event");
                }
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse updateEvent(final Event event) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Update Event");
                validateRequest(event);
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null) {
                    EventEntity eventEntity = eventDao.findEventOfUser(event.getId(), currentUser.getId());
                    if (eventEntity != null) {
                        eventEntity.setName(event.getName());
                        eventEntity.setStartDate(event.getStartDate());
                        eventEntity.setEndDate(event.getEndDate());
                        eventEntity.setImage(event.getImage());
                        eventDao.update(eventEntity);
                        LOGGER.info("Operation executed successfully: Update Event");
                        return true;
                    }
                }
                throw new UTravelerWebServiceException(ErrorCode.USER_NOT_AUTHENTICATED,
                                                       "The user was not authenticated to update this event");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse insertEvents(final List<Event> events) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Events");
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null) {
                    int routesLimit = currentUser.getSetting().getEventsLimit();
                    long routesQuantity = eventDao.getEventsQuantity(currentUser.getId());
                    if (isExceededLimit(routesLimit, routesQuantity + events.size())) {
                        eventDao.insert(createEventEntities(events, currentUser));
                        LOGGER.info("Operation executed successfully: Add Events");
                        return true;
                    }
                    return false;
                } else {
                    throw new UTravelerWebServiceException(ErrorCode.USER_NOT_AUTHENTICATED,
                                                           "The user was not authenticated to add event");
                }
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse deleteEvent(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Delete Event");
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null) {
                    EventEntity event = eventDao.findEventOfUser(eventId, currentUser.getId());
                    if (event != null) {
                        messageDao.deleteFromEvent(eventId);
                        moneySpendingDao.deleteFromEvent(eventId);
                        routeDao.deleteFromEvent(eventId);
                        photoDao.deleteFromEvent(eventId);
                        eventDao.delete(event);
                        LOGGER.info("Operation executed successfully: Delete Event");
                        return true;
                    }
                    return false;
                } else {
                    throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED, "User does not have any permissions to this event");
                }
            }
        });
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getEventsQuantity(final long userId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Events Quantity");
                int eventsQuantity;
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null && currentUser.getId() == userId) {
                    eventsQuantity = eventDao.getEventsQuantity(userId);
                    LOGGER.info("Operation executed successfully: Get Events Quantity");
                } else {
                    throw new UTravelerWebServiceException(ErrorCode.USER_NOT_AUTHENTICATED,
                                                           "The user was not authenticated or does not have permission to view events");
                }
                return eventsQuantity;
            }
        });
    }


    private Collection<EventEntity> createEventEntities(Collection<Event> events, User currentUser) {
        UserEntity userEntity = userDao.getById(currentUser.getId());
        Collection<EventEntity> eventEntities = Lists.newArrayList();
        for (Event event : events) {
            eventEntities.add(validateAndCreateEventEntity(userEntity, event));
        }
        return eventEntities;
    }


    private EventEntity validateAndCreateEventEntity(UserEntity currentUser, Event event) {
        validateRequest(event);
        EventEntity eventEntity = eventMapper.mapModel(event);
        eventEntity.setUser(currentUser);
        return eventEntity;
    }

}
