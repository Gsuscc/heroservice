package com.reactheroes.userservice.dao.implementations;

import com.reactheroes.userservice.dao.interfaces.UserDetailDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import com.reactheroes.userservice.model.Nick;
import com.reactheroes.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class UserDetailJpa implements UserDetailDao {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetail getUserDetail(String email) {
        return userRepository.findByEmailIs(email);
    }

    @Override
    public boolean isUserDetailNotExist(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean isNickAlreadyTaken(String nick) {
        return userRepository.existsByNick(nick);
    }

    @Override
    public Nick getNickByEmail(String email) {
        return new Nick(userRepository.findByEmailIs(email).getNick());
    }

    @Override
    public void createNewUserDetail(String nick, String email) {
        UserDetail userDetail = UserDetail.builder()
                .email(email)
                .nick(nick)
                .balance(1000L)
                .build();
        userRepository.save(userDetail);
    }

    @Override
    public Long getUserBalance(String email) {
       return userRepository.getUserBalance(email);
    }

    @Override
    public void incrementBalance(Long amount, String email) {
        Long currentBalance = userRepository.getUserBalance(email);
        userRepository.setUserBalance(currentBalance + amount, email);
    }

    @Override
    public void decrementBalance(Long amount, String email) {
        Long currentBalance = userRepository.getUserBalance(email);
        if (currentBalance - amount < 0L) throw new NoSuchElementException("Not enough money");
        userRepository.setUserBalance(currentBalance - amount, email);
    }

    @Override
    public Set<HeroCard> getAllHeroCardsByUserEmail(String email) {
        return userRepository.getAllHeroCardsByUserEmail(email);
    }
}
