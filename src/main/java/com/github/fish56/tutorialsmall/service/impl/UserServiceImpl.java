package com.github.fish56.tutorialsmall.service.impl;

import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.repository.UserCrudRepository;
import com.github.fish56.tutorialsmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserCrudRepository repository;

    @Override
    public User postUser(User user) {
        User user1 = repository.findByLogin(user.getLogin());
        if (user1 != null) {
            return user;
        }
        repository.save(user);
        return user;
    }

    @Override
    public boolean checkoutUser(User user) {
        User user1 = repository.findByLogin(user.getLogin());
        return user1.getToken() == user.getToken();
    }

    @Override
    public User findByToken(String token) {
        return repository.findByToken(token);
    }
}
