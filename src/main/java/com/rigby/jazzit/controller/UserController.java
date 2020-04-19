package com.rigby.jazzit.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rigby.jazzit.domain.User;
import com.rigby.jazzit.domain.request.LoginRequest;
import com.rigby.jazzit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

    @Autowired UserService userService;


    @PostMapping("/api/users/register")
    public ResponseEntity<Void> postRegister(@Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<User> postLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/api/users/{userId}/contacts")
    public ResponseEntity<List<User>> getContacts(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findContactsById(userId));
    }

    @PostMapping("/api/users/{userId}/contact")
    public ResponseEntity<Void> postContact(
            @PathVariable Long userId,
            @RequestParam String email
    ) {
        userService.createContact(userId, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
