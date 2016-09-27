package com.utraveler.dao;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.ResetPasswordRecord;

public interface ResetPasswordRecordDao extends Dao<ResetPasswordRecord> {

    String findCodeByUserId(long userId);


    void insertRecord(long userId, String code);


    boolean deleteByUserIdAndCode(long userId, String code);
}
