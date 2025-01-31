package com.paulfloresdev.tweetsappbackend.controller;

import com.paulfloresdev.tweetsappbackend.DAO.Tweet.DeleteTweetResponseDAO;
import com.paulfloresdev.tweetsappbackend.DAO.Tweet.TweetDAO;
import com.paulfloresdev.tweetsappbackend.DAO.Tweet.TweetResponseDAO;
import com.paulfloresdev.tweetsappbackend.services.TweetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    private final TweetsService tweetsService;

    @Autowired
    public TweetsController(TweetsService service) {
        this.tweetsService = service;
    }

    // Post a tweet
    @PostMapping("/post")
    public ResponseEntity<TweetResponseDAO> postTweet(@RequestBody TweetDAO tweet) {
        return this.tweetsService.postTweet(Optional.of(tweet));
    }

    // Get All tweets
    @GetMapping()
    public ResponseEntity<List<TweetResponseDAO>> getAllTweets() {
        return this.tweetsService.getAllTweets();
    }

    // Delete a tweet
    @DeleteMapping("/delete/{idDelete}")
    public ResponseEntity<DeleteTweetResponseDAO> deleteTweet(@PathVariable Long idDelete) {
        return this.tweetsService.deleteTweet(idDelete);
    }


}
