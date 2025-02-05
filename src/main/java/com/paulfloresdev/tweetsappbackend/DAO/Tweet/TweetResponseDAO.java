package com.paulfloresdev.tweetsappbackend.DAO.Tweet;

import com.paulfloresdev.tweetsappbackend.DAO.User.UserResponseDAO;
import com.paulfloresdev.tweetsappbackend.models.Location;
import com.paulfloresdev.tweetsappbackend.models.Tweet;
import com.paulfloresdev.tweetsappbackend.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TweetResponseDAO {
    private String id;
    private User author;
    private String imageUrl;
    private String text;
    private String videoUrl;
    private Location location;
    private boolean hasVideo;
    private boolean hasImage;
    private boolean hasLocation;
    private String createdAt;


    public List<TweetResponseDAO> fromList(List<Tweet> tweets) {
        List<TweetResponseDAO> response = new ArrayList<>();

        for (Tweet tweet : tweets) {
            response.add(fromTweet(tweet));
        }

        return response;
    }

    public TweetResponseDAO fromTweet(Tweet tweet) {
        TweetResponseDAO tweetDAO = new TweetResponseDAO();

        tweetDAO.setId(String.valueOf(tweet.getId()));
        tweetDAO.setAuthor(tweet.getUser());
        tweetDAO.setImageUrl(tweet.getImageUrl());
        tweetDAO.setText(tweet.getText());
        tweetDAO.setVideoUrl(tweet.getVideoUrl());
        tweetDAO.setLocation(tweet.getLocation());
        tweetDAO.setHasVideo(tweet.getVideoUrl() != null && !tweet.getVideoUrl().isEmpty());
        tweetDAO.setHasImage(tweet.getImageUrl() != null && !tweet.getImageUrl().isEmpty());
        tweetDAO.setHasLocation(tweet.getLocation() != null);
        tweetDAO.setCreatedAt(tweet.getCreatedAt());

        return tweetDAO;
    }
}
