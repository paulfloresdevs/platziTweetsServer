package com.paulfloresdev.tweetsappbackend.DAO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDAO implements ResponseDAO {
    private String message;
    private int status;

    public ErrorResponseDAO(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getStatus() {
        return "Status: " + this.status;
    }

    @Override
    public String getMessage() {
        return "Error: " + this.message;
    }
}
