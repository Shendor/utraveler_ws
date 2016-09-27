package com.utraveler.validator;

import com.utraveler.model.TripPlan;
import com.utraveler.validator.util.ValidationUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TripPlanValidator implements Validator {


    public static final int MAX_TEXT_SIZE = 16777215;


    @Override
    public boolean supports(Class<?> targetClass) {
        return TripPlan.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        TripPlan tripPlan = (TripPlan)target;
        if (!ValidationUtil.isTextInRange(tripPlan.getPlanItemsJson(), 0, MAX_TEXT_SIZE)) {
            errors.rejectValue("planItemsJson", "error.validation.tripPlan.planItemsJson");
        }
        if (!ValidationUtil.isTextInRange(tripPlan.getFlightPlanItemsJson(), 0, MAX_TEXT_SIZE)) {
            errors.rejectValue("flightPlanItemsJson", "error.validation.tripPlan.flightPlanItemsJson");
        }
        if (!ValidationUtil.isTextInRange(tripPlan.getRentPlanItemsJson(), 0, MAX_TEXT_SIZE)) {
            errors.rejectValue("rentPlanItemsJson", "error.validation.tripPlan.rentPlanItemsJson");
        }

    }
}
