package com.reactheroes.loginservice.security;

import com.reactheroes.loginservice.entity.UserCredential;
import com.reactheroes.loginservice.repository.UserCredentialsRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserCredentialsRepository users;

    public CustomUserDetailsService(UserCredentialsRepository users) {
        this.users = users;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User object.
     * Spring will call this code to retrieve a user upon login from the DB.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredential userInfo = users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + email + " not found"));

        return new org.springframework.security.core.userdetails.User(userInfo.getEmail(), userInfo.getPassword(),
                new HashSet<GrantedAuthority>());
    }
}