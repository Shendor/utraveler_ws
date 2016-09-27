package com.utraveler.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.utraveler.validator.exception.NoValidatorException;

public class ValidationServiceImpl implements ValidationService {

    private Iterable<Validator> validators;


    public ValidationServiceImpl() {
        validators = Lists.newArrayList();
    }


    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }


    @Override
    public Errors validate(Object target) {
        Errors mapBindingResult = null;
        for (Validator validator : validators) {
            if (validator.supports(target.getClass())) {
                mapBindingResult = new MapBindingResult(Maps.newHashMap(), target.getClass().getName());
                ValidationUtils.invokeValidator(validator, target, mapBindingResult);
            }
        }
        if (mapBindingResult == null) {
            throw new NoValidatorException("No validator found for target: " + target.getClass().getName());
        }
        return mapBindingResult;
    }
}
