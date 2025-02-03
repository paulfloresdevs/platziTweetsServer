package com.paulfloresdev.tweetsappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    private String email;
    private String names;
    private String nickname;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<Tweet> tweets;

    public User(String email, String names, String nickname, String password) {
        this.email = email;
        this.names = names;
        this.nickname = nickname;
        this.password = password;
        this.tweets = new ArrayList<>();
    }

    public User() {

    }
}
