package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.Pushpin;

public class PushpinValidator implements Validator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return Pushpin.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Pushpin pushpin = (Pushpin)target;
        if (pushpin.getCoordinate() == null) {
            errors.rejectValue("coordinate", "error.validation.pushpin.coordinate");
        }
    }
}
