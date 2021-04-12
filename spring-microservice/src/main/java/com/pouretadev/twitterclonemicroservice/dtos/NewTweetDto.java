package com.pouretadev.twitterclonemicroservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NewTweetDto {
    public String text;
    @JsonProperty("author")
    public String authorUsername;
}