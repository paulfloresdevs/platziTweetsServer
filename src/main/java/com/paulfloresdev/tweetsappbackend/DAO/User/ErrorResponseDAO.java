package com.paulfloresdev.tweetsappbackend.DAO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDAO extends ResponseDAO {
    private String message;

    public ErrorResponseDAO(String message) {
        this.message = message;
    }
}
