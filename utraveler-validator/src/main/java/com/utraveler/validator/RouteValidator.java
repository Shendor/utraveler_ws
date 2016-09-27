package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.Route;
import com.utraveler.validator.util.ValidationUtil;

public class RouteValidator implements Validator {


    public static final int MAX_ROUTE_NAME = 1024;
    public static final int MAX_DESCRIPTION_LENGTH = 1024;
    public static final int MAX_JSON_LENGTH = 16777215;


    @Override
    public boolean supports(Class<?> targetClass) {
        return Route.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Route route = (Route)target;
        if (!ValidationUtil.isTextInRange(route.getName(), 0, MAX_ROUTE_NAME)) {
            errors.rejectValue("name", "error.validation.route.name");
        }
        if (!ValidationUtil.isTextInRange(route.getDescription(), 0, MAX_DESCRIPTION_LENGTH)) {
            errors.rejectValue("description", "error.validation.route.description");
        }
        if (!ValidationUtil.isTextInRange(route.getCoordinatesJson(), 0, MAX_JSON_LENGTH)) {
            errors.rejectValue("coordinatesJson", "error.validation.route.coordinatesJson");
        }
        if (!ValidationUtil.isTextInRange(route.getPolygonsJson(), 0, MAX_JSON_LENGTH)) {
            errors.rejectValue("polygonsJson", "error.validation.route.polygonsJson");
        }
        if (!ValidationUtil.isTextInRange(route.getPushpinsJson(), 0, MAX_JSON_LENGTH)) {
            errors.rejectValue("pushpinsJson", "error.validation.route.pushpinsJson");
        }
    }
}
