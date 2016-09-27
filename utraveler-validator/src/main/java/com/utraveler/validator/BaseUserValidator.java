package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.BaseUser;
import com.utraveler.validator.util.ValidationUtil;

public class BaseUserValidator implements Validator {


    @Override
    public boolean supports(Class<?> modelClass) {
        return BaseUser.class.equals(modelClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        BaseUser user = (BaseUser)target;
        if (!ValidationUtil.isTextInRange(user.getDescription(), 0, 1024)) {
            errors.rejectValue("description", "error.validation.user.description");
        }
        if (!ValidationUtil.isTextInRange(user.getName(), 0, 255)) {
            errors.rejectValue("name", "error.validation.user.name");
        }
        if (user.getAvatar() != null && user.getAvatar().length >= 1048576) {
            errors.rejectValue("avatar", "error.validation.user.avatar");
        }
        if (user.getCover() != null && user.getCover().length >= 16777215) {
            errors.rejectValue("cover", "error.validation.user.cover");
        }
    }

}
