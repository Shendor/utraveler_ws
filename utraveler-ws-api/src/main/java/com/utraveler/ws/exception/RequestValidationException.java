package com.utraveler.ws.exception;

import org.springframework.validation.Errors;

import com.utraveler.ws.response.ErrorCode;

public class RequestValidationException extends UTravelerWebServiceException {

    private Errors validationErrors;


    public RequestValidationException(Errors validationErrors) {
        super(ErrorCode.VALIDATION, "Request has validation errors");
        this.validationErrors = validationErrors;
    }


    public Errors getValidationErrors() {
        return validationErrors;
    }
}
