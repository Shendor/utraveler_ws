package com.utraveler.ws.service.impl;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.utraveler.auth.AuthenticationService;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.entity.BaseEntity;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.model.User;
import com.utraveler.validator.ValidationService;
import com.utraveler.ws.exception.LimitExceededException;
import com.utraveler.ws.exception.RequestValidationException;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.response.ResponseError;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public abstract class BaseWebService {

    private static final Logger LOGGER = Logger.getLogger(BaseWebService.class);

    private ValidationService validationService;


    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }


    protected JsonResponse execute(WebServiceMethodExecutor executor) {
        JsonResponse response = new JsonResponse();
        try {
            response.setResponseObject(executor.execute());
        } catch (RequestValidationException rve) {
            response.setError(new ResponseError(rve.getCode(), convertToValidationErrorMessages(rve)));
        } catch (UTravelerWebServiceException wsEx) {
            LOGGER.error("WS Exception was thrown with code: " + wsEx.getCode(), wsEx);
            response.setError(new ResponseError(wsEx.getCode(), wsEx.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("Unexpected Exception was thrown: ", ex);
            response.setError(new ResponseError(ErrorCode.DEFAULT.getCode(),
                                                ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()));
        }
        return response;
    }


    private List<String> convertToValidationErrorMessages(RequestValidationException rve) {
        List<String> errorMessages = Lists.newArrayList();
        for (ObjectError error : rve.getValidationErrors().getAllErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }
        return errorMessages;
    }


    protected void validateRequest(Object target)
            throws RequestValidationException {
        Errors errors = validationService.validate(target);
        if (errors.hasErrors()) {
            throw new RequestValidationException(errors);
        }
    }


    protected Object getInsertedIds(Collection<? extends BaseEntity> entities) {
        if (entities.size() == 1) {
            BaseEntity baseEntity = entities.iterator().next();
            return baseEntity.getId();
        } else {
            Collection<Long> insertedIds = Lists.newArrayList();
            for (BaseEntity entity : entities) {
                insertedIds.add(entity.getId());
            }
            return insertedIds;
        }
    }


    protected User getCurrentUser(AuthenticationService authenticationService) {
        return authenticationService.getCurrentUser();
    }


    protected EventEntity getEventOfUser(long eventId, User user, EventDao eventDao) {
        return eventDao.findEventOfUser(eventId, user.getId());
    }


    protected boolean isEventBelongToUser(long eventId, User user, EventDao eventDao) {
        return user != null && eventDao.isBelongToUser(eventId, user.getId());
    }


    protected boolean isExceededLimit(long limit, long itemsToAdd) {
        if (limit < itemsToAdd) {
            throw new LimitExceededException(itemsToAdd - limit);
        }
        return true;
    }


    protected interface WebServiceMethodExecutor {

        Object execute();
    }
}