package com.pouretadev.twitterclonemicroservice.dtos;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TweetDetailledDto {
    @JsonProperty("id")
    public UUID id;

    @JsonProperty("text")
    public String text;

    @JsonProperty("author")
    public String authorUsername;
    
    @JsonProperty("replyingTo")
    public UUID replyToTweet;

    @JsonProperty("replies")
    public List<UUID> tweetRepliesIds;

    @JsonProperty("timestamp")
    public Timestamp insertedAt;

    @JsonProperty("processedAtTimestamp")
    public Timestamp processedAt;
}
