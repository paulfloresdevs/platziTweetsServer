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

    public ResponseEntity<TweetResponseDAO> postTweet(Optional<TweetDAO> tweetDAOOptional) {
        boolean isValidTweets = TweetsUtils.isValidTweet(tweetDAOOptional);

        if (isValidTweets) {
            TweetDAO tweetDAO = tweetDAOOptional.get();
            User author = this.userService.getUser(tweetDAO.getUserEmail()).get();

            Tweet tweet = TweetsUtils.setTweetParams(tweetDAO, author);
            this.tweetsRepository.save(tweet);

            Optional<Tweet> tweetFromDB = this.tweetsRepository.findById(tweet.getId());

            return tweetFromDB.map(value ->
                            ResponseEntity.ok(new TweetResponseDAO().fromTweet(value)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                    );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<List<TweetResponseDAO>> getAllTweets() {
        List<Tweet> tweets = this.tweetsRepository.findAll();
        TweetResponseDAO responseDAO = new TweetResponseDAO();
        List<TweetResponseDAO> tweetsReponseList = responseDAO.fromList(tweets);

        return ResponseEntity.ok(tweetsReponseList);
    }

    public ResponseEntity<DeleteTweetResponseDAO> deleteTweet(Long id) {
        Optional<Tweet> tweet = tweetsRepository.findById(id);

        if (tweet.isPresent()) {
            try {
                this.tweetsRepository.deleteById(id);
                return ResponseEntity.ok(new DeleteTweetResponseDAO(true, "Tweet deleted"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new DeleteTweetResponseDAO(false, "Error deleting tweet"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DeleteTweetResponseDAO(false, "Tweet not found"));
    }
}
