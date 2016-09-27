package com.utraveler.ws.auth;

import org.springframework.security.core.Authentication;

import com.utraveler.ws.auth.model.UTravelerAuthentication;

public interface SocialNetworkAuthenticationVerifier {

    UTravelerAuthentication verifyAndGetAuthentication(Authentication authentication);
}
