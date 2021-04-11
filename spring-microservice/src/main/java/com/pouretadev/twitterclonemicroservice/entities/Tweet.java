package com.pouretadev.twitterclonemicroservice.entities;

import lombok.*;
import javax.persistence.*;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tweets")
@Data
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    @JsonProperty("id")
    private UUID id;

    @Column(name = "text", length = 280)
    @JsonProperty("text")
    private String text;

    @Column(name = "category", nullable = true)
    @Enumerated(EnumType.STRING)
    private TweetLabel label;

    // Replies to a tweet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replying_to")
    private Tweet replyingTo;

    @OneToMany(mappedBy = "replyingTo", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Tweet> replies;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @CreationTimestamp
    @Column(name = "inserted_at")
    @JsonProperty("timestamp")
    private Timestamp insertedAt;

    @Column(name = "processed_at")
    @JsonProperty("processedTimestamp")
    private Timestamp processedAt;
}
