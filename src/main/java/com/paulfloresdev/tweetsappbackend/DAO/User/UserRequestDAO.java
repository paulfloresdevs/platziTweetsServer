package com.paulfloresdev.tweetsappbackend.DAO.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDAO {
    private String email;
    private String password;
    private String names;
    private String profileImageUrl;
}
