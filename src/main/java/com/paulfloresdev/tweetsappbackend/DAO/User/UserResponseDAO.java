package com.paulfloresdev.tweetsappbackend.DAO.User;

import com.paulfloresdev.tweetsappbackend.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDAO implements ResponseDAO {

    User user;
    private String message;
    private int status;

    public UserResponseDAO(String email, String names, String password, String profileImageUrl) {
        this.message = "Operation successfully completed";
        this.status = 200;
        this.user = new User(
                email,
                names,
                NicknameConstructor(email, names),
                password,
                profileImageUrl
        );
    }

    private String NicknameConstructor(String email, String names){

        StringBuilder nicknameBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                nicknameBuilder.append(email.charAt(i));
            } else {
                nicknameBuilder.append(names.charAt(i));
            }
        }
        return nicknameBuilder.toString();
    }

    @Override
    public String getStatus() {
        return "Status: " + this.status;
    }

    @Override
    public String getMessage() {
        return "Success: " + this.message;
    }
}
