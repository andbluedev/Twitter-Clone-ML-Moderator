package com.pouretadev.twitterclonemicroservice.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

import com.pouretadev.twitterclonemicroservice.repositories.UserRepository;
import com.pouretadev.twitterclonemicroservice.dtos.UserDetailledDto;
import com.pouretadev.twitterclonemicroservice.entities.User;
import com.pouretadev.twitterclonemicroservice.mappers.UserMapper;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RolesAllowed("user")
    @GetMapping("/all")
    public List<UserDetailledDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        logger.info("Fetched {} detailled users: {}", users.size());

        return this.userMapper.mapToListDto(users);
    }
}