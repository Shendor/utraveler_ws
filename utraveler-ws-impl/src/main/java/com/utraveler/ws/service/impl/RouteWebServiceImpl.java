package com.utraveler.ws.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.utraveler.auth.AuthenticationService;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.RouteDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.RouteEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Route;
import com.utraveler.model.User;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.RouteWebService;

public class RouteWebServiceImpl extends BaseWebService implements RouteWebService {

    private static final Logger LOGGER = Logger.getLogger(RouteWebServiceImpl.class);

    private RouteDao routeDao;
    private EventDao eventDao;
    private AuthenticationService authenticationService;
    private Mapper<Route, RouteEntity> routeMapper;


    public void setRouteDao(RouteDao routeDao) {
        this.routeDao = routeDao;
    }


    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    public void setRouteMapper(Mapper<Route, RouteEntity> routeMapper) {
        this.routeMapper = routeMapper;
    }


    @Override
    @Transactional
    public JsonResponse addRoute(final Route route, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Route");
                EventEntity event = getEventOfUser(eventId);
                int routesLimit = getCurrentUser().getSetting().getRoutesLimit();
                long routesQuantity = routeDao.getRoutesQuantity(eventId);
                if (event != null && isExceededLimit(routesLimit, routesQuantity + 1)) {
                    RouteEntity routeEntity = routeMapper.mapModel(route);
                    routeEntity.setEvent(event);
                    validateRequest(route);
                    routeDao.insert(routeEntity);
                    LOGGER.info("Operation executed successfully: Add Route");
                    return routeEntity.getId();
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to this Event");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse deleteRoute(final long routeId, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Delete Route");
                RouteEntity routeEntity = routeDao.findRouteOfEvent(routeId, eventId);
                if (routeEntity != null && isEventBelongToUser(eventId)) {
                    routeDao.delete(routeEntity);
                    LOGGER.info("Operation executed successfully: Delete Route");
                    return true;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to this Route");
            }
        });
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getRoutes(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Routes");
                if (isEventBelongToUser(eventId)) {
                    Collection<Route> routes = Lists.newArrayList();
                    for (RouteEntity routeEntity : routeDao.findRoutesOfEvent(eventId)) {
                        routes.add(routeMapper.mapEntity(routeEntity));
                    }
                    LOGGER.info("Operation executed successfully: Get Routes");
                    return routes;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have permission to this Event");
            }
        });
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
