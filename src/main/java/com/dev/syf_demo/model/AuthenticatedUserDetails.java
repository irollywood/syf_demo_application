package com.dev.syf_demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AuthenticatedUserDetails implements UserDetails {

    private AppUser appUser;

    public AuthenticatedUserDetails(AppUser user) {
        this.appUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return this.appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.appUser.getUsername();
    }

    public Integer  getId() {
        return this.appUser.getId();
    }
}
