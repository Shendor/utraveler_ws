package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.Message;
import com.utraveler.validator.util.ValidationUtil;

public class MessageValidator implements Validator {


    private static final int MAX_TEXT_LENGTH = 4096;


    @Override
    public boolean supports(Class<?> targetClass) {
        return Message.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Message message = (Message)target;
        if (!ValidationUtil.isTextInRange(message.getText(), 0, MAX_TEXT_LENGTH)) {
            errors.rejectValue("text", "error.validation.message.text");
        }
    }
}
