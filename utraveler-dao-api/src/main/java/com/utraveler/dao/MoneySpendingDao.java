package com.utraveler.dao;

import java.util.Collection;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.MoneySpendingEntity;

public interface MoneySpendingDao extends Dao<MoneySpendingEntity>, DeleteEventDao {

    Collection<? extends MoneySpendingEntity> findMoneySpendingsOfEvent(long eventId);


    MoneySpendingEntity findMoneySpendingOfEvent(long moneySpendingId, long eventId);


    long getMoneySpendingsQuantity(long eventId);
}
