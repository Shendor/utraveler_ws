package com.utraveler.dao.mysql;

import java.util.List;

import com.utraveler.dao.ResetPasswordRecordDao;
import com.utraveler.dao.entity.ResetPasswordRecord;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.ResetPasswordRecordImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("resetPasswordRecordDao")
public class ResetPasswordRecordDaoImpl extends BaseDao<ResetPasswordRecord> implements ResetPasswordRecordDao {


    @Override
    public String findCodeByUserId(long userId) {
        ResetPasswordRecordImpl resetPasswordRecord = getResetPasswordRecord(userId);
        return resetPasswordRecord != null ? resetPasswordRecord.getCode() : null;
    }


    @Override
    public void insertRecord(long userId, String code) {
        ResetPasswordRecordImpl record = getResetPasswordRecord(userId);
        if (record == null) {
            record = new ResetPasswordRecordImpl();
        }
        record.setUserId(userId);
        record.setCode(code);
        if (record.getId() <= 0) {
            insert(record);
        } else {
            update(record);
        }
    }


    @Override
    public boolean deleteByUserIdAndCode(long userId, String code) {
        Query query = getSession().getNamedQuery("ResetPasswordRecord.deleteByUserIdAndCode");
        query.setLong("userId", userId);
        query.setString("code", code);
        return query.executeUpdate() > 0;
    }


    @Override
    public ResetPasswordRecord getById(long id) {
        return (ResetPasswordRecord)getSession().get(ResetPasswordRecordImpl.class, id);
    }


    private ResetPasswordRecordImpl getResetPasswordRecord(long userId) {
        Query query = getSession().getNamedQuery("ResetPasswordRecord.findResetPasswordRecordByUserId");
        query.setLong("userId", userId);
        List list = query.list();
        return list != null && !list.isEmpty() ? (ResetPasswordRecordImpl)list.get(0) : null;
    }

}
