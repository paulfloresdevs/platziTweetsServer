package com.paulfloresdev.tweetsappbackend.Utils;

import com.paulfloresdev.tweetsappbackend.DAO.Tweet.TweetDAO;
import com.paulfloresdev.tweetsappbackend.models.Location;
import com.paulfloresdev.tweetsappbackend.models.Tweet;
import com.paulfloresdev.tweetsappbackend.models.User;
import com.paulfloresdev.tweetsappbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TweetsUtils {
    static UserService userService;

    @Autowired
    public TweetsUtils(UserService userService) {
        TweetsUtils.userService = userService;
    }


    public static boolean isValidTweet(Optional<TweetDAO> tweetDAO) {

        if (tweetDAO.isEmpty()) return false;

        boolean isTextPresent = tweetDAO.get().getText() != null && !tweetDAO.get().getText().isEmpty();
        boolean isLatitudePresent = tweetDAO.get().getLatitude() != 0;
        boolean isLongitudePresent = tweetDAO.get().getLongitude() != 0;
        boolean isUserExist = userService.isUserExist(tweetDAO.get().getUserId());

        return isTextPresent && isLatitudePresent && isLongitudePresent && isUserExist;
    }

    public static Tweet setTweetParams(TweetDAO tweetDAO, User user) {
        Location location = new Location(tweetDAO.getLatitude(), tweetDAO.getLongitude());

        return new Tweet(
                tweetDAO.getText(),
                tweetDAO.getImageUrl(),
                tweetDAO.getVideoUrl(),
                new Location(tweetDAO.getLatitude(), tweetDAO.getLongitude()),
                user
        );
    }
}
