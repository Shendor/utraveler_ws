package com.utraveler.validator;

import org.springframework.validation.Errors;

import com.utraveler.model.User;
import com.utraveler.validator.util.ValidationUtil;

public class UserValidator extends BaseUserValidator {

    public static final int MIN_PASSWORD_LENGTH = 3;
    public static final int MAX_PASSWORD_LENGTH = 128;


    @Override
    public boolean supports(Class<?> modelClass) {
        return User.class.equals(modelClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;
        super.validate(target, errors);
        if (!ValidationUtil.isEmailValid(user.getEmail())) {
            errors.rejectValue("email", "error.validation.user.email");
        }
        if (!ValidationUtil.isTextInRange(user.getPassword(), MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)) {
            errors.rejectValue("password", "error.validation.user.password");
        }
    }
}
