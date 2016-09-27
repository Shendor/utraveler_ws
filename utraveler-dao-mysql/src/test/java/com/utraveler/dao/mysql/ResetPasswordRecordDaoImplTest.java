package com.utraveler.dao.mysql;

import com.utraveler.dao.ResetPasswordRecordDao;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import com.utraveler.dao.mysql.entity.ResetPasswordRecordImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.RESET_PASSWORD_RECORD_TABLE})
public class ResetPasswordRecordDaoImplTest extends UTravelerTestFixture {

    private ResetPasswordRecordDao resetPasswordRecordDao;


    @Before
    public void setUp() {
        resetPasswordRecordDao = context.getBean(ResetPasswordRecordDao.class);
    }


    @Test
    public void testInsertRecord_RecordSetCorrectly_RecordInserted() {
        ResetPasswordRecordImpl record = new ResetPasswordRecordImpl();
        record.setCode("code1");
        record.setUserId(100);

        resetPasswordRecordDao.insert(record);

        assertNotNull(resetPasswordRecordDao.findCodeByUserId(record.getUserId()));
    }


    @Test
    public void testDeleteRecord_RecordSetCorrectly_RecordDeleted() {
        ResetPasswordRecordImpl record = new ResetPasswordRecordImpl();
        record.setCode("code1");
        record.setUserId(100);

        resetPasswordRecordDao.insert(record);
        resetPasswordRecordDao.deleteByUserIdAndCode(record.getUserId(), record.getCode());

        assertNull(resetPasswordRecordDao.findCodeByUserId(record.getUserId()));
    }

}