package com.utraveler.dao.mysql;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.utraveler.dao.UserSettingDao;
import com.utraveler.dao.entity.UserSettingEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.UserSettingEntityImpl;

@Repository("userSettingDao")
public class UserSettingDaoImpl extends BaseDao<UserSettingEntity> implements UserSettingDao {

    @Override
    public UserSettingEntity getSettingForUser(long userId) {
        Query query = getSession().getNamedQuery("UserSetting.getSettingForUser");
        query.setParameter("userId", userId);
        return (UserSettingEntity)query.uniqueResult();
    }


    @Override
    public UserSettingEntity getById(long id) {
        return (UserSettingEntity)getSession().get(UserSettingEntityImpl.class, id);
    }
}
