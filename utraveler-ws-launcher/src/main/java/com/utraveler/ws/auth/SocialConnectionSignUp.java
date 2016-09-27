/*
package com.utraveler.ws.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import com.utraveler.auth.AuthenticationService;

public class SocialConnectionSignUp implements ConnectionSignUp {

    private AuthenticationService authenticationService;


    public SocialConnectionSignUp(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();
        UserDetails userDetails = loadUser(connection.createData().getAccessToken(), profile.getEmail());

        return userDetails.getUsername();
    }


    private UserDetails loadUser(String accessToken, String email) {
        UserDetails user = null;
        if (authenticationService.isEmailExist(email)) {
            authenticationService.registerUser(email, accessToken);

//            JsonResponse response = authenticationService.findUserByUserName(email);
//            user = ((UserDetails)response.getResponseObject());
        }
        return user;
    }

}
*/
