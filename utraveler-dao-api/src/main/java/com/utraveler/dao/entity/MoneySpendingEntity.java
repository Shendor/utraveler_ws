package com.utraveler.dao.entity;

import org.joda.time.DateTime;

public interface MoneySpendingEntity extends BaseEntity {

    EventEntity getEvent();


    void setEvent(EventEntity eventId);


    DateTime getDate();


    void setDate(DateTime date);


    Float getAmount();


    void setAmount(Float amount);


    MoneySpendingCategory getMoneySpendingCategory();


    void setMoneySpendingCategory(MoneySpendingCategory moneySpendingCategory);


    CurrencyType getCurrency();


    void setCurrency(CurrencyType currency);


    String getDescription();


    void setDescription(String description);
}
