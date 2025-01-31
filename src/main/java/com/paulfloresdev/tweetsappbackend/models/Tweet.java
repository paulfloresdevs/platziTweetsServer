package com.paulfloresdev.tweetsappbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String imageUrl;
    private String videoUrl;
    private Location location;
    private String createdAt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    public Tweet(String text, String imageUrl, String videoUrl, Location location, User user) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.location = location;
        this.user = user;

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        this.createdAt = String.valueOf(formattedDate);
    }
}
