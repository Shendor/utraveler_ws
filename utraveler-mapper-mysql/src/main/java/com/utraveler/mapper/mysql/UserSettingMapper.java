package com.utraveler.mapper.mysql;

import com.utraveler.dao.entity.UserSettingEntity;
import com.utraveler.dao.mysql.entity.UserSettingEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.UserSetting;

public class UserSettingMapper implements Mapper<UserSetting, UserSettingEntity> {

    @Override
    public UserSettingEntity mapModel(UserSetting model) {
        UserSettingEntityImpl userSettingEntity = new UserSettingEntityImpl();
        userSettingEntity.setId(model.getId());
        userSettingEntity.setBackgroundColor(model.getBackgroundColor());
        userSettingEntity.setMainColor(model.getMainColor());
        userSettingEntity.setTextColor(model.getTextColor());
        userSettingEntity.setCoverOpacity(model.getCoverOpacity());
        userSettingEntity.setLimitCode(model.getLimitCode());
        return userSettingEntity;
    }


    @Override
    public UserSetting mapEntity(UserSettingEntity entity) {
        UserSetting userSetting = new UserSetting();
        if (entity != null) {
            userSetting.setId(entity.getId());
            userSetting.setBackgroundColor(entity.getBackgroundColor());
            userSetting.setMainColor(entity.getMainColor());
            userSetting.setTextColor(entity.getTextColor());
            userSetting.setCoverOpacity(entity.getCoverOpacity());
            userSetting.setLimitCode(entity.getLimitCode());
        }
        return userSetting;
    }
}
