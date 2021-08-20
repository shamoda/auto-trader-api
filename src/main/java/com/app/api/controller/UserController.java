package com.app.api.controller;

import com.app.api.exception.AutoTraderException;
import com.app.api.model.User;
import com.app.api.repository.UserRepository;
import com.app.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService service;
    private final UserRepository repository;

    @Autowired
    public UserController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/user")
    public ResponseEntity<?> insertUser(@RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("name") String name,
                                        @RequestParam("role") String role,
                                        @RequestParam("contact") String contact,
                                        @RequestParam("location") String location)
    {
        User temUser = new User(email, name, contact, role, password, location);
        return new ResponseEntity<>(service.insertUser(temUser), HttpStatus.CREATED);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestParam("email") String email,
                                        @RequestParam("name") String name,
                                        @RequestParam("role") String role,
                                        @RequestParam("contact") String contact,
                                        @RequestParam("location") String location)
    {
        User u = repository.findById(email).get();
        User temUser = new User(email, name, contact, role, u.getPassword(), location);
        return new ResponseEntity<>(service.insertUser(temUser), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/user/filter")
    public ResponseEntity<?> getUserByExample(@RequestBody User user) {
        return new ResponseEntity<>(service.retriveByExample(user), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
        } catch (AutoTraderException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        service.deleteById(id);
        return new ResponseEntity<>("user recode deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/user/login/{email}")
    public ResponseEntity<?> login(@PathVariable String email)
    {
        try {
            return new ResponseEntity<>(service.login(email), HttpStatus.OK);
        } catch (AutoTraderException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
