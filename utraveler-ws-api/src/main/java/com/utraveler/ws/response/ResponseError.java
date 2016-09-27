package com.utraveler.ws.response;

import java.io.Serializable;

public class ResponseError implements Serializable {

    private String errorCode;
    private Object error;


    public ResponseError() {
    }


    public ResponseError(String errorCode, Object error) {
        this.errorCode = errorCode;
        this.error = error;
    }


    public String getErrorCode() {
        return errorCode;
    }


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    public Object getError() {
        return error;
    }


    public void setError(Object error) {
        this.error = error;
    }
}
