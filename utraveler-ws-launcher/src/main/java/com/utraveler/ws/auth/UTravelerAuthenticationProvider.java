package com.utraveler.ws.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.utraveler.ws.auth.model.UTravelerAuthentication;

public class UTravelerAuthenticationProvider extends DaoAuthenticationProvider {

    private SocialNetworkAuthenticationVerifier facebookAuthVerifier;


    public void setFacebookAuthVerifier(SocialNetworkAuthenticationVerifier facebookAuthVerifier) {
        this.facebookAuthVerifier = facebookAuthVerifier;
    }


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() != null) {
            UTravelerAuthentication fbAuth = facebookAuthVerifier.verifyAndGetAuthentication(authentication);
            if (fbAuth != null) {
                authentication = fbAuth;
            }
        }
        return super.authenticate(authentication);
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (!(authentication instanceof UTravelerAuthentication)) {
            super.additionalAuthenticationChecks(userDetails, authentication);
        }
    }

}
