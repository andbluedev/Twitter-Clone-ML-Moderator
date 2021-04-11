package com.pouretadev.twitterclonemicroservice.dtos;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDetailledDto {
    @JsonProperty("uuid")
    public UUID id;

    @JsonProperty("id")
    public String username;

    @JsonProperty("avatarURL")
    public String avatarUrl;

    @JsonProperty("tweets")
    private List<UUID> tweets;
}
