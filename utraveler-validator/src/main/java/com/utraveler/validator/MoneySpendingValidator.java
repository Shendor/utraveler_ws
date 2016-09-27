package com.utraveler.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.utraveler.model.MoneySpending;
import com.utraveler.validator.util.ValidationUtil;

public class MoneySpendingValidator implements Validator {

    private static final int MAX_DESCRIPTION_LENGTH = 16384;


    @Override
    public boolean supports(Class<?> targetClass) {
        return MoneySpending.class.equals(targetClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        MoneySpending moneySpending = (MoneySpending)target;
        if (!ValidationUtil.isNumberInRange(moneySpending.getAmount(), 0, Float.MAX_VALUE)) {
            errors.rejectValue("amount", "error.validation.moneyspending.amount");
        }
        if (moneySpending.getCurrency() == null) {
            errors.rejectValue("currency", "error.validation.moneyspending.currency");
        }
        if (moneySpending.getMoneySpendingCategory() == null) {
            errors.rejectValue("category", "error.validation.moneyspending.category");
        }
        if (!ValidationUtil.isTextInRange(moneySpending.getDescription(), 0, MAX_DESCRIPTION_LENGTH)) {
            errors.rejectValue("description", "error.validation.moneyspending.description");
        }
    }
}
