package com.reactheroes.loginservice.dao.interfaces;

import com.reactheroes.loginservice.entity.UserCredential;

public interface UserCredentialsDao {

    void registerUser(UserCredential userCredential);

    UserCredential getUserByEmail(String email);

    boolean isExistingUser(String email);


}
