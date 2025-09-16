package com.dev.syf_demo.service;

import com.dev.syf_demo.model.AppUser;
import com.dev.syf_demo.model.AuthenticatedUserDetails;
import com.dev.syf_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepository.findAppUsersByUsername(username);

        if(user == null){ throw new UsernameNotFoundException("Username not found"); }

        return  new AuthenticatedUserDetails(user);
    }
}
