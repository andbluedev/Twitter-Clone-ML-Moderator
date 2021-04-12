package com.pouretadev.twitterclonemicroservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pouretadev.twitterclonemicroservice.entities.User;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findAll();

    Optional<User> findByUsername(String username);
}
