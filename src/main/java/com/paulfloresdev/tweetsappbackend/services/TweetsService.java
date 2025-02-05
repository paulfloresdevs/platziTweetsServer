package com.paulfloresdev.tweetsappbackend.services;

import com.paulfloresdev.tweetsappbackend.DAO.Tweet.DeleteTweetResponseDAO;
import com.paulfloresdev.tweetsappbackend.DAO.Tweet.TweetDAO;
import com.paulfloresdev.tweetsappbackend.DAO.Tweet.TweetResponseDAO;
import com.paulfloresdev.tweetsappbackend.Repository.TweetsRepository;
import com.paulfloresdev.tweetsappbackend.Utils.TweetsUtils;
import com.paulfloresdev.tweetsappbackend.models.Tweet;
import com.paulfloresdev.tweetsappbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TweetsService {
    private final TweetsRepository tweetsRepository;
    private final UserService userService;

    @Autowired
    public TweetsService(TweetsRepository tweetsRepository, UserService userService) {
        this.tweetsRepository = tweetsRepository;
        this.userService = userService;
    }

    /*
        Post a tweet
        Params: TweetDAO
     */
    public ResponseEntity<TweetResponseDAO> postTweet(Optional<TweetDAO> tweetDAOOptional) {
        // Check if the tweet has all the required fields
        boolean isValidTweets = TweetsUtils.isValidTweet(tweetDAOOptional);

        // If the tweet is valid, save it to the database
        if (isValidTweets && tweetDAOOptional.isPresent()) {

            // Get the user that is posting the tweet
            User author = this.userService.getUser(tweetDAOOptional.get().getUserEmail()).get();

            // Set the tweet parameters
            Tweet tweet = TweetsUtils.setTweetParams(tweetDAOOptional.get(), author);

            // Save the tweet to the database
            this.tweetsRepository.save(tweet);

            // Get the tweet from the database for know if it was saved correctly
            Optional<Tweet> tweetFromDB = this.tweetsRepository.findById(tweet.getId());

            // Return the tweet if it was saved correctly, otherwise return an error
            return tweetFromDB.map(value ->
                            ResponseEntity.ok(new TweetResponseDAO().fromTweet(value)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                    );
        }
        // Return an error if the tweet is not valid
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /*
        Get all tweets
        Params: None
     */
    public ResponseEntity<List<TweetResponseDAO>> getAllTweets() {

        // Get all tweets from the database
        List<Tweet> tweets = this.tweetsRepository.findAll();

        // Convert the tweets to a list of TweetResponseDAO
        TweetResponseDAO responseDAO = new TweetResponseDAO();
        List<TweetResponseDAO> tweetsReponseList = responseDAO.fromList(tweets);

        // Return the list of tweets response DAO
        return ResponseEntity.ok(tweetsReponseList);
    }

    /*
        Delete a tweet
        Params: Long id
     */
    public ResponseEntity<DeleteTweetResponseDAO> deleteTweet(Long id) {

        // Get the tweet from the database
        Optional<Tweet> tweet = tweetsRepository.findById(id);

        // If the tweet exists, delete it
        if (tweet.isPresent()) {
            try {
                this.tweetsRepository.deleteById(id);
                return ResponseEntity.ok(new DeleteTweetResponseDAO(true, "Tweet deleted"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new DeleteTweetResponseDAO(false, "Error deleting tweet"));
            }
        }

        // Return an error if the tweet does not exist
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DeleteTweetResponseDAO(false, "Tweet not found"));
    }
}
