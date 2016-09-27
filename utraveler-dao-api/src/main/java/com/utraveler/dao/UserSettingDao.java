package com.utraveler.dao;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.UserSettingEntity;

public interface UserSettingDao extends Dao<UserSettingEntity> {

    UserSettingEntity getSettingForUser(long userId);
}
