package com.utraveler.mapper.mysql;

import com.utraveler.dao.entity.MoneySpendingEntity;
import com.utraveler.dao.mysql.entity.MoneySpendingEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.MoneySpending;

public class MoneySpendingMapper implements Mapper<MoneySpending, MoneySpendingEntity> {

    @Override
    public MoneySpendingEntity mapModel(MoneySpending model) {
        MoneySpendingEntityImpl moneySpendingEntity = new MoneySpendingEntityImpl();
        moneySpendingEntity.setId(model.getId());
        moneySpendingEntity.setCurrency(model.getCurrency());
        moneySpendingEntity.setMoneySpendingCategory(model.getMoneySpendingCategory());
        moneySpendingEntity.setDate(model.getDate());
        moneySpendingEntity.setChangeDate(model.getChangeDate());
        moneySpendingEntity.setAmount(model.getAmount());
        moneySpendingEntity.setDescription(model.getDescription());

        return moneySpendingEntity;
    }


    @Override
    public MoneySpending mapEntity(MoneySpendingEntity entity) {
        MoneySpending moneySpending = new MoneySpending();
        moneySpending.setId(entity.getId());
        moneySpending.setCurrency(entity.getCurrency());
        moneySpending.setMoneySpendingCategory(entity.getMoneySpendingCategory());
        moneySpending.setDate(entity.getDate());
        moneySpending.setChangeDate(entity.getChangeDate());
        moneySpending.setAmount(entity.getAmount());
        moneySpending.setDescription(entity.getDescription());

        return moneySpending;
    }
}
