package com.utraveler.ws.service.impl;

import com.utraveler.auth.AuthenticationService;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.TripPlanDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.TripPlanEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.TripPlan;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.TripPlanWebService;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class TripPlanWebServiceImpl extends BaseWebService implements TripPlanWebService {

    private static final Logger LOGGER = Logger.getLogger(TripPlanWebServiceImpl.class);

    private TripPlanDao tripPlanDao;
    private Mapper<TripPlan, TripPlanEntity> tripPlanMapper;
    private EventDao eventDao;
    private AuthenticationService authenticationService;


    public void setTripPlanDao(TripPlanDao tripPlanDao) {
        this.tripPlanDao = tripPlanDao;
    }


    public void setTripPlanMapper(Mapper<TripPlan, TripPlanEntity> tripPlanMapper) {
        this.tripPlanMapper = tripPlanMapper;
    }


    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getTripPlanOfEvent(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Trip Plan");
                EventEntity event = getEventOfUser(eventId, getCurrentUser(authenticationService), eventDao);
                if (event != null) {
                    TripPlanEntity tripPlanEntity = tripPlanDao.findTripPlanOfEvent(eventId);
                    if (tripPlanEntity != null) {
                        return tripPlanMapper.mapEntity(tripPlanEntity);
                    }
                    LOGGER.info("Operation executed successfully: Get Trip Plan");
                    return null;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Event entity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse addTripPlanToEvent(final TripPlan tripPlan, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Trip Plan");
                EventEntity event = getEventOfUser(eventId, getCurrentUser(authenticationService), eventDao);
                if (tripPlanDao.findTripPlanOfEvent(eventId) == null) {
                    TripPlanEntity tripPlanEntity = createTripPlanEntity(event, tripPlan);
                    tripPlanDao.insert(tripPlanEntity);

                    LOGGER.info("Operation executed successfully: Add Trip Plan");
                    return tripPlanEntity.getId();
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Event entity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse deleteTripPlanFromEvent(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Delete Trip Plan");
                if (isEventBelongToUser(eventId, getCurrentUser(authenticationService), eventDao)) {
                    tripPlanDao.deleteFromEvent(eventId);
                    LOGGER.info("Operation executed successfully: Delete Trip Plan");
                    return true;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Event entity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse updateTripPlan(final TripPlan tripPlan, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Update Trip Plan");
                validateRequest(tripPlan);
                TripPlanEntity tripPlanEntity = tripPlanDao.findTripPlanOfEvent(eventId);
                if (tripPlanEntity != null) {
                    tripPlanEntity.setPlanItemsJson(tripPlan.getPlanItemsJson());
                    tripPlanEntity.setFlightPlanItemsJson(tripPlan.getFlightPlanItemsJson());
                    tripPlanEntity.setRentPlanItemsJson(tripPlan.getRentPlanItemsJson());

                    tripPlanDao.update(tripPlanEntity);
                    LOGGER.info("Operation executed successfully: Update Trip Plan");
                    return true;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Message entity");
            }
        });
    }


    private TripPlanEntity createTripPlanEntity(EventEntity event, TripPlan tripPlan) {
        validateRequest(tripPlan);
        TripPlanEntity tripPlanEntity = tripPlanMapper.mapModel(tripPlan);
        tripPlanEntity.setEvent(event);
        return tripPlanEntity;
    }
}
