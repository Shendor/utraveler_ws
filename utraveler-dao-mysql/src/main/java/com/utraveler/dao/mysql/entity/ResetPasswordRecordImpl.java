package com.utraveler.dao.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.utraveler.dao.entity.ResetPasswordRecord;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity(name = "reset_password_record")
@NamedQueries({
        @NamedQuery(name = "ResetPasswordRecord.findResetPasswordRecordByUserId",
                query = "SELECT rpr FROM reset_password_record AS rpr " +
                        "WHERE rpr.userId=:userId ORDER BY rpr.changeDate DESC"),

        @NamedQuery(name = "ResetPasswordRecord.deleteByUserIdAndCode",
                query = "DELETE FROM reset_password_record AS rpr " +
                        "WHERE rpr.userId=:userId AND rpr.code=:code"),
})
public class ResetPasswordRecordImpl extends AbstractEntity implements ResetPasswordRecord {

    private long userId;
    private String code;


    @Override
    @Column(name = "code")
    public String getCode() {
        return code;
    }


    @Override
    public void setCode(String code) {
        this.code = code;
    }


    @Override
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }


    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

}
