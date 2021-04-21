package com.pouretadev.twitterclonemicroservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pouretadev.twitterclonemicroservice.entities.User;
import com.pouretadev.twitterclonemicroservice.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOrCreateUser(String username) {
        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            User newUser = new User();

            newUser.setUsername(username);
            newUser.setAvatarUrl(String.format("https://github.com/%s.png?size=200", username));

            User createdUser = this.userRepository.save(newUser);
            logger.info("'{}' user added to database (uuid: '{}'')", createdUser.getUsername(), createdUser.getId());

            return createdUser;
        }
        
        logger.info("'{}' found user in database", user.getUsername());

        return user;
    }

}