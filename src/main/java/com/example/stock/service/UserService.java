package com.example.stock.service;

import com.example.stock.entity.User;
import com.example.stock.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long userId) {
        logger.info("Calling findById with userId: {}", userId);

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            logger.info("User found: id={}, name={}, email={}", user.get().getId(), user.get().getName(), user.get().getEmail());
        } else {
            logger.warn("User not found for id={}", userId);
        }

        return user;
    }



    public Optional<User> findByEmail(String email) {
        logger.info("Searching for user by email: {}", email);

        return userRepository.findByEmail(email);
    }




}
