package com.utraveler.mapper.impl;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import com.utraveler.dao.entity.CurrencyType;
import com.utraveler.dao.entity.MoneySpendingCategory;
import com.utraveler.dao.entity.MoneySpendingEntity;
import com.utraveler.dao.entity.impl.MoneySpendingEntityImpl;
import com.utraveler.model.MoneySpending;
import static org.testng.Assert.assertEquals;

public class MoneySpendingMapperTest {

    @Test
    public void testMapEntity_AllFieldsProvided_MappingSuccess()
            throws Exception {
        MoneySpendingMapper moneySpendingMapper = new MoneySpendingMapper();

        MoneySpendingEntityImpl moneySpendingEntity = new MoneySpendingEntityImpl();
        moneySpendingEntity.setId(100);
        moneySpendingEntity.setCurrency(CurrencyType.Euro);
        moneySpendingEntity.setMoneySpendingCategory(MoneySpendingCategory.Camping);
        moneySpendingEntity.setDate(DateTime.now());
        moneySpendingEntity.setAmount(200f);
        moneySpendingEntity.setDescription("Description");

        MoneySpending moneySpending = moneySpendingMapper.mapEntity(moneySpendingEntity);

        assertEquals(moneySpending.getId(), moneySpendingEntity.getId());
        assertEquals(moneySpending.getCurrency(), moneySpendingEntity.getCurrency());
        assertEquals(moneySpending.getMoneySpendingCategory(), moneySpendingEntity.getMoneySpendingCategory());
        assertEquals(moneySpending.getDate(), moneySpendingEntity.getDate());
        assertEquals(moneySpending.getAmount(), moneySpendingEntity.getAmount());
        assertEquals(moneySpending.getDescription(), moneySpendingEntity.getDescription());
    }


    @Test
    public void testMapModel_AllFieldsProvided_MappingSuccess()
            throws Exception {
        MoneySpendingMapper moneySpendingMapper = new MoneySpendingMapper();

        MoneySpending moneySpending = new MoneySpending();
        moneySpending.setId(100);
        moneySpending.setCurrency(CurrencyType.Euro);
        moneySpending.setMoneySpendingCategory(MoneySpendingCategory.Camping);
        moneySpending.setDate(DateTime.now());
        moneySpending.setAmount(200f);
        moneySpending.setDescription("Description");

        MoneySpendingEntity moneySpendingEntity = moneySpendingMapper.mapModel(moneySpending);

        assertEquals(moneySpendingEntity.getId(), moneySpending.getId());
        assertEquals(moneySpendingEntity.getCurrency(), moneySpending.getCurrency());
        assertEquals(moneySpendingEntity.getMoneySpendingCategory(), moneySpending.getMoneySpendingCategory());
        assertEquals(moneySpendingEntity.getDate(), moneySpending.getDate());
        assertEquals(moneySpendingEntity.getAmount(), moneySpending.getAmount());
        assertEquals(moneySpendingEntity.getDescription(), moneySpending.getDescription());
    }
}
