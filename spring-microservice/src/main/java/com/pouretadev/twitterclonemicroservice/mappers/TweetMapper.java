package com.pouretadev.twitterclonemicroservice.mappers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

import com.pouretadev.twitterclonemicroservice.entities.Tweet;
import com.pouretadev.twitterclonemicroservice.dtos.TweetDetailledDto;

public class TweetMapper {
    public TweetDetailledDto mapToDto(Tweet source) {
        TweetDetailledDto.TweetDetailledDtoBuilder builder = TweetDetailledDto.builder();
        UUID replyingTo = null;
        if (source.getReplyingTo() != null) {
            replyingTo = source.getReplyingTo().getId();
        }

        TweetDetailledDto result = builder
            .text(source.getText())
            .id(source.getId())
            .replyToTweet(replyingTo)
            .tweetRepliesIds(new LinkedList<UUID>())
            .insertedAt(source.getInsertedAt())
            .profanityScore(source.getProfanityScore())
            .label(source.getLabel())
            .processedAt(source.getProcessedAt())
            .authorUsername(source.getAuthor().getUsername())
            .build();


        for (Tweet reply : source.getReplies()) {
            result.tweetRepliesIds.add(reply.getId());
        }

        return result;
    }

    public List<TweetDetailledDto> mapToListDto(List<Tweet> sourceList) {
        return sourceList.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
