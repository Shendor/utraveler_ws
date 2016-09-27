package com.utraveler.ws.response;

public enum ErrorCode {
    DEFAULT("000"),
    USER_NOT_AUTHENTICATED("001"),
    ACCESS_DENIED("002"),
    WRONG_ENTITY_RELATIONS("003"),
    BAD_CREDENTIALS("004"),
    VALIDATION("005"),
    LIMIT("006");

    private String code;


    ErrorCode(String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }
}

