package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.Event;
import com.utraveler.validator.util.ValidationUtil;

public class EventValidator implements Validator {

    private static final int MAX_EVENT_NAME_LENGTH = 255;
    private static final int MAX_EVENT_IMAGE_LENGTH = 700000;


    @Override
    public boolean supports(Class<?> targetClass) {
        return Event.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event)target;
        if (!ValidationUtil.isTextInRange(event.getName(), 0, MAX_EVENT_NAME_LENGTH)) {
            errors.rejectValue("name", "error.validation.event.name");
        }
        if (event.getImage() != null && event.getImage().length >= MAX_EVENT_IMAGE_LENGTH) {
            errors.rejectValue("image", "error.validation.event.image");
        }
    }
}
