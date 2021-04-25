package com.pouretadev.twitterclonemicroservice.entities;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@JsonIgnoreProperties({"tweets"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    @JsonProperty("id")
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "username", length = 100)
    @JsonProperty("name")
    private String username;

    @Column(name = "avatar_url")
    @JsonProperty("avatarURL")
    private String avatarUrl;

    @Generated(GenerationTime.INSERT)
    @Column(name = "inserted_at")
    private Timestamp insertedAt;
    
    @JsonProperty("tweets")
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Tweet> tweets;
}