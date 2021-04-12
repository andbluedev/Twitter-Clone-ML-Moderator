package com.pouretadev.twitterclonemicroservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pouretadev.twitterclonemicroservice.entities.Tweet;

@Service
public class MLQueueService {
    @Value("${application.allowed_origin}")
    private String frontendUrl;
    private static final Logger logger = LoggerFactory.getLogger(MLQueueService.class);

    private ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MLQueueService(final RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;

    }

    public void sendTweet(Tweet tweet) {
        try {
            String orderJson = objectMapper.writeValueAsString(tweet);

            Message message = MessageBuilder.withBody(orderJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON).build();

            logger.info("Sending new tweet with uuid '{}' to queue...", tweet.getId());
            logger.info("routing: '{}' exchange: '{}'", rabbitTemplate.getRoutingKey(), rabbitTemplate.getExchange());

            rabbitTemplate.convertAndSend(rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey(), message);
            logger.info("'{}' successfully sent to processing queue", tweet.getId());

        } catch (JsonProcessingException e) {
            logger.error("Error while converting new tweet with uuid '{}' to JSON format.", tweet.getId());
            e.printStackTrace();
        }
    }
}