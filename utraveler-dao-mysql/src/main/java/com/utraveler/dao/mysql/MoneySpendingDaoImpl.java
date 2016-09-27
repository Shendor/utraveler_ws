package com.utraveler.dao.mysql;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.utraveler.dao.MoneySpendingDao;
import com.utraveler.dao.entity.MoneySpendingEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.MoneySpendingEntityImpl;

@Repository("moneySpendingDao")
public class MoneySpendingDaoImpl extends BaseDao<MoneySpendingEntity> implements MoneySpendingDao {

    @Override
    public boolean deleteFromEvent(long eventId) {
        Query query = getSession().getNamedQuery("MoneySpending.deleteFromEvent");
        query.setLong("eventId", eventId);
        return query.executeUpdate() > 0;
    }


    @Override
    public boolean deleteFromEvent(Collection<Long> entitiesId, long eventId) {
        Query query = getSession().getNamedQuery("MoneySpending.deleteMoneySpendingsFromEvent");
        query.setLong("eventId", eventId);
        query.setParameterList("entitiesId", entitiesId);
        return query.executeUpdate() > 0;
    }


    @Override
    public Collection<? extends MoneySpendingEntity> findMoneySpendingsOfEvent(long eventId) {
        Query query = getSession().getNamedQuery("MoneySpending.findMoneySpendingsOfEvent");
        query.setLong("eventId", eventId);
        return query.list();
    }


    @Override
    public MoneySpendingEntity findMoneySpendingOfEvent(long moneySpendingId, long eventId) {
        Query query = getSession().getNamedQuery("MoneySpending.findMoneySpendingOfEvent");
        query.setLong("id", moneySpendingId);
        query.setLong("eventId", eventId);
        return (MoneySpendingEntity)query.uniqueResult();
    }


    @Override
    public MoneySpendingEntity getById(long id) {
        return (MoneySpendingEntity)getSession().get(MoneySpendingEntityImpl.class, id);
    }


    @Override
    public long getMoneySpendingsQuantity(long eventId) {
        Query query = getSession().getNamedQuery("MoneySpending.getMoneySpendingsQuantity");
        query.setLong("eventId", eventId);

        return (Long)query.uniqueResult();
    }
}
