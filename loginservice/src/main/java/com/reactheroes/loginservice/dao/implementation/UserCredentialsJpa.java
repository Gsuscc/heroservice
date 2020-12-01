package com.reactheroes.loginservice.dao.implementation;

import com.reactheroes.loginservice.dao.interfaces.UserCredentialsDao;
import com.reactheroes.loginservice.entity.UserCredential;
import com.reactheroes.loginservice.repository.UserCredentialsRepository;
import com.reactheroes.loginservice.util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserCredentialsJpa implements UserCredentialsDao {

    private final PasswordEncoder passwordEncoder;
    UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public UserCredentialsJpa(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void registerUser(UserCredential userCredential) {
        if(!EmailValidator.validate(userCredential.getEmail())){
            throw new IllegalArgumentException("Not a valid email format");
        }
        if(isExistingUser(userCredential.getEmail())){
            throw new SecurityException("This email had been taken!");
        }
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialsRepository.save(userCredential);
    }

    @Override
    public UserCredential getUserByEmail(String email) {
        return userCredentialsRepository.findByEmail(email).get();
    }

    @Override
    public boolean isExistingUser(String email) {
        return userCredentialsRepository.existsByEmail(email);
    }
}
