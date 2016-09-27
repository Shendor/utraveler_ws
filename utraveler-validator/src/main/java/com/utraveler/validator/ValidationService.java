package com.utraveler.validator;

import org.springframework.validation.Errors;

public interface ValidationService {

    Errors validate(Object target);
}
