package com.rigby.jazzit.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rigby.jazzit.domain.User;
import com.rigby.jazzit.domain.request.LoginRequest;
import com.rigby.jazzit.domain.response.LoginResponse;
import com.rigby.jazzit.security.SecurityIgnore;
import com.rigby.jazzit.service.UserService;
import com.rigby.jazzit.service.tool.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

    @Autowired private UserService userService;

    @SecurityIgnore
    @PostMapping("/api/users/register")
    public ResponseEntity<Void> postRegister(@Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @SecurityIgnore
    @PostMapping("/api/users/login")
    public ResponseEntity<LoginResponse> postLogin(@Valid @RequestBody LoginRequest loginRequest) {

        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setBirthday(user.getBirthday());
        loginResponse.setToken(JwtTool.createToken(user.getEmail()));

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/api/users/{userId}/contacts")
    public ResponseEntity<List<User>> getContacts(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findContactsById(userId));
    }

    @PostMapping("/api/users/{userId}/contacts")
    public ResponseEntity<Void> postContacts(
            @PathVariable Long userId,
            @Email @RequestParam String email
    ) {
        userService.createContact(userId, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
