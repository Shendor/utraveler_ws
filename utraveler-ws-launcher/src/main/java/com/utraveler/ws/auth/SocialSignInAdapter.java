/*
package com.utraveler.ws.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.DefaultAuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.utraveler.ws.service.UserWebService;

public class SocialSignInAdapter implements SignInAdapter {

    private UserWebService userWebService;
    private DefaultTokenServices defaultTokenServices;


    public SocialSignInAdapter(UserWebService userWebService, DefaultTokenServices defaultTokenServices) {
        this.userWebService = userWebService;
        this.defaultTokenServices = defaultTokenServices;
    }


    @Override
    public String signIn(String userName, Connection<?> connection, NativeWebRequest request) {
        UserDetails user = (UserDetails)userWebService.getUserByUserName(userName);

        if (user == null) {
            throw new InvalidAuthorizationException("User " + userName + " was not found");
        } else {
            Map<String, String> authorizationParameters = Maps.newHashMap();
            authorizationParameters.put("username", user.getUsername());
            authorizationParameters.put("client_id", "utraveler-wp");
            authorizationParameters.put("client_secret", "utraveler_secret_code");
            authorizationParameters.put("grant_type", "password");

            DefaultAuthorizationRequest authorizationRequest = new DefaultAuthorizationRequest(authorizationParameters);
            authorizationRequest.setApproved(true);

            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            authorizationRequest.setAuthorities(authorities);

            HashSet<String> resourceIds = Sets.newHashSet();
            resourceIds.add("mobile-public");
            authorizationRequest.setResourceIds(resourceIds);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);

            OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest, authenticationToken);
            authenticationRequest.setAuthenticated(true);

            OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(authenticationRequest);

            HttpServletRequest req = request.getNativeRequest(HttpServletRequest.class);

            authenticationToken.setDetails(new WebAuthenticationDetails(req));

            SecurityContextHolder.getContext().setAuthentication(authenticationRequest);


            // rememberMeServices().loginSuccess((HttpServletRequest)request.getNativeRequest(),
            //                                   (HttpServletResponse)request.getNativeResponse(), token);

        }
        return "/oauth/token?grant_type=password&client_id=utraveler-wp&client_secret=utraveler_secret_code" +
               "&username=shan&password=123";
    }
}*/
