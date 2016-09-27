package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.UserSetting;
import com.utraveler.validator.util.ValidationUtil;

public class UserSettingsValidator implements Validator {

    public static final int COLOR_LENGTH = 9;


    @Override
    public boolean supports(Class<?> targetClass) {
        return UserSetting.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        UserSetting userSetting = (UserSetting)target;
        if (!ValidationUtil.isTextEqualsLength(userSetting.getTextColor(), COLOR_LENGTH)) {
            errors.rejectValue("text_color", "error.validation.user_settings.text_color");
        }
        if (!ValidationUtil.isTextEqualsLength(userSetting.getBackgroundColor(), COLOR_LENGTH)) {
            errors.rejectValue("background_color", "error.validation.user_settings.background_color");
        }
        if (!ValidationUtil.isTextEqualsLength(userSetting.getMainColor(), COLOR_LENGTH)) {
            errors.rejectValue("main_color", "error.validation.user_settings.main_color");
        }
        if (!ValidationUtil.isNumberInRange(userSetting.getCoverOpacity(), 0, 1)) {
            errors.rejectValue("cover_opacity", "error.validation.user_settings.cover_opacity");
        }
        if (!ValidationUtil.isNumberInRange(userSetting.getCoverOpacity(), 0, 1)) {
            errors.rejectValue("cover_opacity", "error.validation.user_settings.cover_opacity");
        }
        if (!(userSetting.getLimitCode().equals(UserSetting.LC7U12RI3THJX)
              || userSetting.getLimitCode().equals(UserSetting.LCUV5T03R93GV))) {
            errors.rejectValue("limit_code", "error.validation.user_settings.limit_code");
        }
    }
}
