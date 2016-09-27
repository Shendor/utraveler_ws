package com.utraveler.auth;

import com.utraveler.model.User;

public interface AuthenticationService {

    User registerUser(String email, String password);


    long registerUser(User user);


    boolean isEmailExist(String email);


    boolean isSameAsAuthenticatedUser(long userId);


    User getCurrentUser();


    boolean isAuthenticated();


    boolean requestResetPassword(String email);


    boolean resetPassword(String email, String requestCode, String newPassword);
}
