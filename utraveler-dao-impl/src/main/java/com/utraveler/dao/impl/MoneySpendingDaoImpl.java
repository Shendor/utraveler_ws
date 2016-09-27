package com.utraveler.dao.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.utraveler.dao.MoneySpendingDao;
import com.utraveler.dao.common.impl.MongoDbBaseDao;
import com.utraveler.dao.entity.MoneySpendingEntity;
import com.utraveler.dao.entity.impl.MoneySpendingEntityImpl;

public class MoneySpendingDaoImpl extends MongoDbBaseDao<MoneySpendingEntity> implements MoneySpendingDao {

    public MoneySpendingDaoImpl(MongoOperations mongoOperations) {
        super(mongoOperations);
    }


    @Override
    public Collection<? extends MoneySpendingEntity> getMoneySpendingsForEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        return mongoOperations.find(query, MoneySpendingEntityImpl.class);
    }


    @Override
    protected String getTableName() {
        return "money_spending";
    }


    @Override
    public MoneySpendingEntity getById(long id) {
        return mongoOperations.findById(id, MoneySpendingEntityImpl.class);
    }


    @Override
    public void deleteFromEvent(long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId));
        mongoOperations.remove(query, MoneySpendingEntityImpl.class);
    }


    @Override
    public void deleteFromEvent(Collection<Long> entitiesId, long eventId) {
        Query query = Query.query(Criteria.where("eventId").is(eventId).and("id").in(entitiesId));
        mongoOperations.remove(query, MoneySpendingEntityImpl.class);
    }
}
