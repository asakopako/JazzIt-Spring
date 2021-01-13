package com.rigby.jazzit.service;

import com.rigby.jazzit.config.exception.BadRequestException;
import com.rigby.jazzit.config.exception.NotFoundException;
import com.rigby.jazzit.domain.User;
import com.rigby.jazzit.repository.UserRepository;
import com.rigby.jazzit.security.SecurityAspect;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private SecurityAspect securityAspect;


    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    public User login(String email, String password) {
        if(!userRepository.existsByEmail(email))
            throw new BadRequestException("Invalid credentials");

        User user = userRepository.findByEmail(email);

        if (!BCrypt.checkpw(password, user.getPassword()))
            throw new BadRequestException("Invalid credentials");

        return user;
    }

    public List<User> findContactsById(Long id) {
        if(!userRepository.existsById(id))
            throw new NotFoundException("User id doesn't exist");

        return userRepository.getOne(id).getContacts();
    }

    public void createContact(Long userId, String email) {
        if(!userRepository.existsById(userId) || !userRepository.existsByEmail(email)) {
            throw new NotFoundException("User id or email doesn't exist");
        }

        List<User> userIdContacts = userRepository.getOne(userId).getContacts();

        if(!userIdContacts.stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList()).isEmpty()) {
            throw new BadRequestException("Contact already added");
        }

        User user = userRepository.getOne(userId);
        user.addContact(userRepository.findByEmail(email));
        userRepository.save(user);
    }

    public List<User> findAllLessThis() {
        return userRepository.findAll().stream().filter(u -> !u.getId().equals(securityAspect.getUserId())).collect(Collectors.toList());
    }

    public User findById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException();
        }
        return userRepository.findById(id).get();
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
