package com.pouretadev.twitterclonemicroservice.mappers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

import com.pouretadev.twitterclonemicroservice.entities.Tweet;
import com.pouretadev.twitterclonemicroservice.entities.User;
import com.pouretadev.twitterclonemicroservice.dtos.UserDetailledDto;

public class UserMapper {
    public UserDetailledDto mapToDto(User source) {
        UserDetailledDto.UserDetailledDtoBuilder builder = UserDetailledDto.builder();

        UserDetailledDto result = builder
            .username(source.getUsername())
            .avatarUrl(source.getAvatarUrl())
            .id(source.getId())
            .tweets(new LinkedList<UUID>())
            .build();

        for (Tweet tweet : source.getTweets()) {
            result.getTweets().add(tweet.getId());
        }

        return result;
    }

    public List<UserDetailledDto> mapToListDto(List<User> sourceList) {
        return sourceList.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
