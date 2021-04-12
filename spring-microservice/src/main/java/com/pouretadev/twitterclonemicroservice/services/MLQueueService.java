package com.pouretadev.twitterclonemicroservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pouretadev.twitterclonemicroservice.entities.Tweet;

@Service
public class MLQueueService {
    @Value("${application.allowed_origin}")
    private String frontendUrl;
    private static final Logger logger = LoggerFactory.getLogger(MLQueueService.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MLQueueService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    public void sendTweet(Tweet tweet) {
        final var message = "From spring";
        logger.info("Sending new tweet with uuid '{}' to queue...", tweet.getId());
        logger.info("routing: '{}' exchange: '{}'", rabbitTemplate.getRoutingKey(), rabbitTemplate.getExchange());
        rabbitTemplate.convertAndSend(rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey(), message);
        logger.info("'{}' successfully sent to processing queue", tweet.getId());
    }
}