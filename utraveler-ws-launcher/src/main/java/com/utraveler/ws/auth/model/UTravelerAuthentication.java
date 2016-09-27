package com.utraveler.ws.auth.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UTravelerAuthentication extends UsernamePasswordAuthenticationToken {

    private boolean isRequirePasswordCheck = true;


    public UTravelerAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }


    public boolean isRequirePasswordCheck() {
        return isRequirePasswordCheck;
    }


    public void setRequirePasswordCheck(boolean isRequirePasswordCheck) {
        this.isRequirePasswordCheck = isRequirePasswordCheck;
    }
}
