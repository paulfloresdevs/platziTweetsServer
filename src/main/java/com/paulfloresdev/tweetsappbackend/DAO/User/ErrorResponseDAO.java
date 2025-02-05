package com.paulfloresdev.tweetsappbackend.DAO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDAO extends ResponseDAO {
    public ErrorResponseDAO(String message) {
        super(message);
    }
}
