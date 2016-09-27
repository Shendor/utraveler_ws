package com.utraveler.model;

import com.utraveler.dao.entity.CurrencyType;
import com.utraveler.dao.entity.MoneySpendingCategory;
import org.joda.time.DateTime;

public class MoneySpending extends BaseModel {

    private MoneySpendingCategory moneySpendingCategory;
    private float amount;
    private String description;
    private CurrencyType currency;
    private DateTime date;


    public MoneySpendingCategory getMoneySpendingCategory() {
        return moneySpendingCategory;
    }


    public void setMoneySpendingCategory(MoneySpendingCategory moneySpendingCategory) {
        this.moneySpendingCategory = moneySpendingCategory;
    }


    public float getAmount() {
        return amount;
    }


    public void setAmount(float amount) {
        this.amount = amount;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public CurrencyType getCurrency() {
        return currency;
    }


    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }


    public DateTime getDate() {
        return date;
    }


    public void setDate(DateTime date) {
        this.date = date;
    }
}
