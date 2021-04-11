package com.pouretadev.twitterclonemicroservice.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.annotation.security.RolesAllowed;

import com.pouretadev.twitterclonemicroservice.config.exceptionhandling.exception.ResourceNotFoundException;
import com.pouretadev.twitterclonemicroservice.dtos.NewTweetDto;
import com.pouretadev.twitterclonemicroservice.dtos.TweetDetailledDto;
import com.pouretadev.twitterclonemicroservice.entities.Tweet;
import com.pouretadev.twitterclonemicroservice.entities.TweetLabel;
import com.pouretadev.twitterclonemicroservice.entities.User;
import com.pouretadev.twitterclonemicroservice.mappers.TweetMapper;
import com.pouretadev.twitterclonemicroservice.repositories.TweetRepository;
import com.pouretadev.twitterclonemicroservice.repositories.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final TweetMapper tweetMapper = new TweetMapper();
    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    // @RolesAllowed("user")
    @GetMapping("/all")
    public List<TweetDetailledDto> getAllTweets() {
        List<Tweet> tweets = this.tweetRepository.findAll();

        logger.info("Fetched {} detailled tweets:", tweets.size());

        return this.tweetMapper.mapToListDto(tweets);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet createTweet(@RequestBody NewTweetDto newTweetDto) {
        User user = this.userRepository.findByUsername(newTweetDto.getAuthorUsername())
                .orElseThrow(ResourceNotFoundException::new);

        logger.info("{} posting new tweet", user.getUsername());

        Tweet newTweet = new Tweet();

        newTweet.setAuthor(user);
        newTweet.setText(newTweetDto.getText());
        newTweet.setLabel(TweetLabel.unprocessed);

        return this.tweetRepository.save(newTweet);
    }
    
    @PostMapping("/{uid}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet replyingToTweet(@RequestBody NewTweetDto newTweetDto, @PathVariable("uid") UUID id) {
        User author = this.userRepository.findByUsername(newTweetDto.getAuthorUsername())
                .orElseThrow(ResourceNotFoundException::new);

        Tweet tweet = this.tweetRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        logger.info("{} replying to {}", newTweetDto.getAuthorUsername(), id);

        Tweet newTweet = new Tweet();

        newTweet.setAuthor(author);
        newTweet.setText(newTweetDto.getText());
        newTweet.setLabel(TweetLabel.unprocessed);
        newTweet.setReplyingTo(tweet);

        return this.tweetRepository.save(newTweet);
    }

}