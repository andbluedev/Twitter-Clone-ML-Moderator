package com.pouretadev.twitterclonemicroservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pouretadev.twitterclonemicroservice.entities.Tweet;
import java.util.UUID;
import java.util.List;

public interface TweetRepository extends CrudRepository<Tweet, UUID> {
    List<Tweet> findAll();
}
