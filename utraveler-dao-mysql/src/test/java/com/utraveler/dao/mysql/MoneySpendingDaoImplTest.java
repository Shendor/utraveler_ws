package com.utraveler.dao.mysql;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.utraveler.dao.MoneySpendingDao;
import com.utraveler.dao.entity.CurrencyType;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.MoneySpendingCategory;
import com.utraveler.dao.entity.MoneySpendingEntity;
import com.utraveler.dao.mysql.common.TestPersistenceDao;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import com.utraveler.dao.mysql.entity.MoneySpendingEntityImpl;
import com.utraveler.dao.mysql.entity.UserEntityImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.MONEY_SPENDING_TABLE, UTravelerTestFixture.EVENT_TABLE, UTravelerTestFixture.USER_TABLE})
public class MoneySpendingDaoImplTest extends UTravelerTestFixture {

    private MoneySpendingDao moneySpendingDao;
    private TestPersistenceDao testPersistenceDao;
    private UserEntityImpl user;


    @Before
    public void setUp() {
        moneySpendingDao = context.getBean(MoneySpendingDao.class);
        testPersistenceDao = context.getBean(TestPersistenceDao.class);
        user = testPersistenceDao.saveUser("User");
    }


    @Test
    public void testInsert() {
        MoneySpendingEntity message = saveMoneySpending();

        assertNotNull(getById(moneySpendingDao, message));
    }


    @Test
    public void testUpdate() {
        MoneySpendingEntity message = saveMoneySpending();
        message.setAmount(100f);
        moneySpendingDao.update(message);

        assertEquals(message.getAmount(), getById(moneySpendingDao, message).getAmount());
    }


    @Test
    public void testDeleteMoneySpendingFromEvent() {
        MoneySpendingEntity message = saveMoneySpending();

        moneySpendingDao.deleteFromEvent(Lists.newArrayList(message.getId()), message.getEvent().getId());

        assertNull(getById(moneySpendingDao, message));
    }


    @Test
    public void testDeleteAllMoneySpendingsFromEvent() {
        MoneySpendingEntity message = saveMoneySpending();

        moneySpendingDao.deleteFromEvent(message.getEvent().getId());

        assertNull(getById(moneySpendingDao, message));
    }


    @Test
    public void testFindMoneySpendingsOfEvent_MoneySpendingsFound() {
        MoneySpendingEntity moneySpendingEntity = saveMoneySpending();

        assertNotNull(moneySpendingDao.findMoneySpendingOfEvent(moneySpendingEntity.getId(), moneySpendingEntity.getEvent().getId()));
        assertEquals(1, moneySpendingDao.findMoneySpendingsOfEvent(moneySpendingEntity.getEvent().getId()).size());
        assertEquals(1, moneySpendingDao.getMoneySpendingsQuantity(moneySpendingEntity.getEvent().getId()));
    }


    private MoneySpendingEntity saveMoneySpending() {
        EventEntity event = testPersistenceDao.saveEvent(user);
        MoneySpendingEntity moneySpending = createMoneySpending(event);
        moneySpendingDao.insert(moneySpending);
        return moneySpending;
    }


    private MoneySpendingEntity createMoneySpending(EventEntity event) {
        MoneySpendingEntity moneySpendingEntity = new MoneySpendingEntityImpl();
        moneySpendingEntity.setDate(DateTime.now());
        moneySpendingEntity.setAmount(12f);
        moneySpendingEntity.setCurrency(CurrencyType.Euro);
        moneySpendingEntity.setDescription("Description");
        moneySpendingEntity.setMoneySpendingCategory(MoneySpendingCategory.Camping);
        moneySpendingEntity.setEvent(event);
        return moneySpendingEntity;
    }
}
