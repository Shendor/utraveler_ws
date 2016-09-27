package com.utraveler.ws.auth.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;
import com.utraveler.model.Role;
import com.utraveler.model.User;
import com.utraveler.model.UserSetting;

public class UTravelerUser implements UserDetails {

    private User user;


    public UTravelerUser(User user, UserSetting userSetting) {
        this.user = user;
        this.user.setSetting(userSetting);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lists.newArrayList(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
    }


    @Override
    public String getPassword() {
        return (user != null) ? user.getPassword() : null;
    }


    @Override
    public String getUsername() {
        return (user != null) ? user.getEmail() : null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return user != null;
    }


    @Override
    public boolean isAccountNonLocked() {
        return user != null;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return user != null;
    }


    @Override
    public boolean isEnabled() {
        return user != null;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

}
