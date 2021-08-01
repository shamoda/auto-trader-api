package com.app.api.service;

import com.app.api.exception.AutoTraderException;
import com.app.api.model.SparePart;
import com.app.api.model.User;
import com.app.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public List<User> retriveByExample(User user) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withMatcher("name", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<User> example = Example.of(user, matcher);
        return repository.findAll(example);
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

    public User login(String email) throws AutoTraderException {
        return repository.findById(email).orElseThrow(
                () -> new AutoTraderException("No such user found.")
        );
    }
}
