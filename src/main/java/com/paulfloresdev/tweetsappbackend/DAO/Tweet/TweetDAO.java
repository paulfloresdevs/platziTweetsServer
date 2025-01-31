package com.paulfloresdev.tweetsappbackend.DAO.Tweet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetDAO {
    private String text;

    private String imageUrl;
    private String videoUrl;

    private Double latitude;
    private Double longitude;

    private Long userId;
}
