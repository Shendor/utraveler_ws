package com.utraveler.ws.exception;

import com.utraveler.ws.response.ErrorCode;

public class UTravelerWebServiceException extends RuntimeException {

    private ErrorCode code;


    public UTravelerWebServiceException(String message) {
        this(ErrorCode.DEFAULT, message);
    }


    public UTravelerWebServiceException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }


    public UTravelerWebServiceException(ErrorCode code, Throwable cause) {
        super(cause.getMessage(), cause);
        this.code = code;
    }


    public String getCode() {
        return code.getCode();
    }
}
