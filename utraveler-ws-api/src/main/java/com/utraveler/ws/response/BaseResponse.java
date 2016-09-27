package com.utraveler.ws.response;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    private ResponseError error;


    public ResponseError getError() {
        return error;
    }


    public void setError(ResponseError error) {
        this.error = error;
    }
}
