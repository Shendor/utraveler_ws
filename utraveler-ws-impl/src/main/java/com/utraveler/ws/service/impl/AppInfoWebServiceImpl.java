package com.utraveler.ws.service.impl;

import com.utraveler.dao.AppInfoDao;
import com.utraveler.dao.entity.AppInfoEntity;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.AppInfoWebService;
import org.springframework.transaction.annotation.Transactional;

public class AppInfoWebServiceImpl extends BaseWebService implements AppInfoWebService {

    private AppInfoDao appInfoDao;


    public void setAppInfoDao(AppInfoDao appInfoDao) {
        this.appInfoDao = appInfoDao;
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getCurrentAppInfo() {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                AppInfoEntity currentAppInfo = appInfoDao.getById(1);
                if (currentAppInfo != null) {
                    return currentAppInfo.getVersion();
                } else {
                    return "1.0";
                }
            }
        });
    }
}
