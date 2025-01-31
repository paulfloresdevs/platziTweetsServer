package com.paulfloresdev.tweetsappbackend.DAO.Tweet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteTweetResponseDAO {
    private boolean deleted;
    private String message;

    public DeleteTweetResponseDAO(boolean deleted, String message) {
        this.deleted = deleted;
        this.message = message;
    }
}
