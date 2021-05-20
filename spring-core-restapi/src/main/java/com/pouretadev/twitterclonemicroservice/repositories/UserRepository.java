package com.pouretadev.twitterclonemicroservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pouretadev.twitterclonemicroservice.entities.User;

import java.util.UUID;
import java.util.List;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findAll();

    User findByUsername(String username);
}
