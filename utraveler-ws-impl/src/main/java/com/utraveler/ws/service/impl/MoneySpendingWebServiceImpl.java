package com.utraveler.ws.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.utraveler.auth.AuthenticationService;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.MoneySpendingDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.MoneySpendingEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.MoneySpending;
import com.utraveler.model.User;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.MoneySpendingWebService;

public class MoneySpendingWebServiceImpl extends BaseWebService implements MoneySpendingWebService {

    private static final Logger LOGGER = Logger.getLogger(MoneySpendingWebServiceImpl.class);

    private MoneySpendingDao moneySpendingDao;
    private EventDao eventDao;
    private AuthenticationService authenticationService;
    private Mapper<MoneySpending, MoneySpendingEntity> moneySpendingMapper;


    public void setMoneySpendingDao(MoneySpendingDao moneySpendingDao) {
        this.moneySpendingDao = moneySpendingDao;
    }


    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    public void setMoneySpendingMapper(Mapper<MoneySpending, MoneySpendingEntity> moneySpendingMapper) {
        this.moneySpendingMapper = moneySpendingMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getMoneySpendingsForEvent(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Money Spendings");
                if (isEventBelongToUser(eventId)) {
                    Collection<MoneySpending> moneySpendings = Lists.newArrayList();
                    for (MoneySpendingEntity entity : moneySpendingDao.findMoneySpendingsOfEvent(eventId)) {
                        moneySpendings.add(moneySpendingMapper.mapEntity(entity));
                    }
                    LOGGER.info("Operation executed successfully: Get Money Spendings");
                    return moneySpendings;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Money Spending entity");
            }
        });
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getTotalSpentMoneyForEvent(long eventId) {
        return new JsonResponse();
    }


    @Override
    @Transactional
    public JsonResponse addMoneySpending(final MoneySpending moneySpending, final long eventId) {
        return addMoneySpendings(Lists.newArrayList(moneySpending), eventId);
    }


    @Override
    @Transactional
    public JsonResponse addMoneySpendings(final Collection<MoneySpending> moneySpendings, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Money Spending");
                EventEntity event = getEventOfUser(eventId);
                int moneySpendingsLimit = getCurrentUser().getSetting().getMoneySpendingsLimit();
                long moneySpendingsQuantity = moneySpendingDao.getMoneySpendingsQuantity(eventId);
                if (event != null && isExceededLimit(moneySpendingsLimit, moneySpendingsQuantity + moneySpendings.size())) {
                    Collection<MoneySpendingEntity> entities = Lists.newArrayList();
                    for (MoneySpending moneySpending : moneySpendings) {
                        entities.add(validateAndCreateMoneySpendingEntity(event, moneySpending));
                    }
                    moneySpendingDao.insert(entities);
                    LOGGER.info("Operation executed successfully: Add Money Spendings");
                    return getInsertedIds(entities);
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Money Spending entity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse deleteMoneySpending(final long moneySpendingId, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Delete Money Spending");
                MoneySpendingEntity moneySpending = moneySpendingDao.findMoneySpendingOfEvent(moneySpendingId, eventId);
                if (moneySpending != null) {
                    moneySpendingDao.delete(moneySpending);
                    LOGGER.info("Operation executed successfully: Delete Money Spendings");
                    return true;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Money Spending entity");
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


    private MoneySpendingEntity validateAndCreateMoneySpendingEntity(EventEntity event, MoneySpending moneySpending) {
        validateRequest(moneySpending);
        MoneySpendingEntity moneySpendingEntity = moneySpendingMapper.mapModel(moneySpending);
        moneySpendingEntity.setEvent(event);

        return moneySpendingEntity;
    }


    private User getCurrentUser() {
        return authenticationService.getCurrentUser();
    }
}
