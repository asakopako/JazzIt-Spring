package com.rigby.jazzit.service;

import com.rigby.jazzit.config.exception.BadRequestException;
import com.rigby.jazzit.config.exception.NotFoundException;
import com.rigby.jazzit.domain.User;
import com.rigby.jazzit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired UserRepository userRepository;


    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        if (!userRepository.existsByEmailAndPassword(email, password)) {
            throw new BadRequestException("Incorrect email or password");
        }
        return userRepository.findByEmail(email);
    }

    public List<User> findContactsById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new NotFoundException("User id doesn't exist");
        }
        return userRepository.getOne(id).getContacts();
    }

    public void createContact(Long userId, String email) {
        if(!userRepository.existsById(userId)) {
            throw new NotFoundException("User id doesn't exist");
        }
        if(!userRepository.existsByEmail(email)) {
            throw new NotFoundException("Email doesn't exist");
        }
        User user = userRepository.getOne(userId);
        user.addContact(userRepository.findByEmail(email));
        userRepository.save(user);
    }
}
