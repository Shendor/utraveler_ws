package com.utraveler.dao.mysql;

import com.utraveler.dao.AppInfoDao;
import com.utraveler.dao.entity.AppInfoEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.AppInfoEntityImpl;
import org.springframework.stereotype.Repository;

@Repository("appInfoDao")
public class AppInfoDaoImpl extends BaseDao<AppInfoEntity> implements AppInfoDao {

    @Override
    public AppInfoEntity getById(long id) {
        return (AppInfoEntity)getSession().get(AppInfoEntityImpl.class, id);
    }
}
