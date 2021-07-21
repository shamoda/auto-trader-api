package com.app.api.service;

import com.app.api.exception.AutoTraderException;
import com.app.api.model.User;
import com.app.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User insertUser(User user) {
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(String id) throws AutoTraderException {
        return repository.findById(id).orElseThrow(
                () -> new AutoTraderException("No such user found.")
        );
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public User login(String email, String pwd) {
        User tempUser = repository.findById(email).get();
        if (tempUser == null) {
            return null;
        } else if (!tempUser.getPassword().equals(pwd)) {
            return null;
        } else {
            return tempUser;
        }
    }
}
