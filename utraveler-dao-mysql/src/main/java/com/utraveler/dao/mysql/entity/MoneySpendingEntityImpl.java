package com.utraveler.dao.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.utraveler.dao.entity.CurrencyType;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.MoneySpendingCategory;
import com.utraveler.dao.entity.MoneySpendingEntity;

@Entity(name = "money_spending")
@NamedQueries({
                      @NamedQuery(name = "MoneySpending.findMoneySpendingsOfEvent",
                                  query = "SELECT ms FROM money_spending AS ms " +
                                          "WHERE ms.event.id=:eventId"),

                      @NamedQuery(name = "MoneySpending.getMoneySpendingsQuantity",
                                  query = "SELECT COUNT(ms) FROM money_spending AS ms " +
                                          "WHERE ms.event.id=:eventId"),

                      @NamedQuery(name = "MoneySpending.findMoneySpendingOfEvent",
                                  query = "SELECT ms FROM money_spending AS ms " +
                                          "WHERE ms.id=:id AND ms.event.id=:eventId"),

                      @NamedQuery(name = "MoneySpending.deleteFromEvent",
                                  query = "DELETE FROM money_spending AS ms " +
                                          "WHERE ms.event.id=:eventId"),

                      @NamedQuery(name = "MoneySpending.deleteMoneySpendingsFromEvent",
                                  query = "DELETE FROM money_spending AS ms " +
                                          "WHERE ms.event.id=:eventId AND ms.id IN (:entitiesId)"),
              })
public class MoneySpendingEntityImpl extends AbstractEntity implements MoneySpendingEntity {

    private DateTime date;
    private Float amount;
    private MoneySpendingCategory moneySpendingCategory;
    private CurrencyType currency;
    private String description;
    private EventEntity event;


    @Override
    @Column(name = "date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getDate() {
        return date;
    }


    @Override
    public void setDate(DateTime date) {
        this.date = date;
    }


    @Override
    @Column
    public Float getAmount() {
        return amount;
    }


    @Override
    public void setAmount(Float amount) {
        this.amount = amount;
    }


    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
//    @Column(name = "category", columnDefinition = "enum('Undefined') DEFAULT 'Undefined'", nullable = false, length = 10)
    public MoneySpendingCategory getMoneySpendingCategory() {
        return moneySpendingCategory;
    }


    @Override
    public void setMoneySpendingCategory(MoneySpendingCategory moneySpendingCategory) {
        this.moneySpendingCategory = moneySpendingCategory;
    }


    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
//    @Column(name = "currency", columnDefinition = "enum('Undefined') DEFAULT 'Undefined'", nullable = false, length = 10)
    public CurrencyType getCurrency() {
        return currency;
    }


    @Override
    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }


    @Override
    @Column(length = 1024, nullable = true)
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EventEntityImpl.class)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    public EventEntity getEvent() {
        return event;
    }


    @Override
    public void setEvent(EventEntity event) {
        this.event = event;
    }
}
