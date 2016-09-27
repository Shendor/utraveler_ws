package com.utraveler.ws.response;

import org.joda.time.DateTime;

public class JsonResponse extends BaseResponse {

    private Object responseObject;
    private DateTime dateTime;


    public JsonResponse() {
        this(null);
    }


    public JsonResponse(Object responseObject) {
        this.responseObject = responseObject;
        dateTime = DateTime.now();
    }


    public Object getResponseObject() {
        return responseObject;
    }


    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }


    public DateTime getDateTime() {
        return dateTime;
    }


    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
