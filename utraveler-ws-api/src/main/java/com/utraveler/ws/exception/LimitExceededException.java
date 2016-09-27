package com.utraveler.ws.exception;

import com.utraveler.ws.response.ErrorCode;

public class LimitExceededException extends UTravelerWebServiceException {

    public static final String MESSAGE = "Exceeded limit for entity: %s";


    public LimitExceededException(long exceededValue) {
        super(ErrorCode.LIMIT, String.format(MESSAGE, exceededValue));
    }

}
