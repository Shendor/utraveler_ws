package com.utraveler.dao.entity.impl;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.utraveler.dao.entity.CurrencyType;
import com.utraveler.dao.entity.MoneySpendingCategory;
import com.utraveler.dao.entity.MoneySpendingEntity;

@Document(collection = "money_spending")
public class MoneySpendingEntityImpl implements MoneySpendingEntity {

    @Id
    private long id;
    private long eventId;
    private DateTime date;
    private Float amount;
    private MoneySpendingCategory moneySpendingCategory;
    private CurrencyType currency;
    private String description;


    @Override
    public long getId() {
        return id;
    }


    @Override
    public void setId(long id) {
        this.id = id;
    }


    @Override
    public long getEventId() {
        return eventId;
    }


    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }


    @Override
    public DateTime getDate() {
        return date;
    }


    @Override
    public void setDate(DateTime date) {
        this.date = date;
    }


    @Override
    public Float getAmount() {
        return amount;
    }


    @Override
    public void setAmount(Float amount) {
        this.amount = amount;
    }


    @Override
    public MoneySpendingCategory getMoneySpendingCategory() {
        return moneySpendingCategory;
    }


    @Override
    public void setMoneySpendingCategory(MoneySpendingCategory moneySpendingCategory) {
        this.moneySpendingCategory = moneySpendingCategory;
    }


    @Override
    public CurrencyType getCurrency() {
        return currency;
    }


    @Override
    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }


    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
