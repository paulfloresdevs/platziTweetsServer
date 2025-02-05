package com.paulfloresdev.tweetsappbackend.DAO.User;

import com.paulfloresdev.tweetsappbackend.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDAO extends ResponseDAO {
    User user;

    public UserResponseDAO(String email, String names, String password) {
        this.user = new User(
                email,
                names,
                NicknameConstructor(email, names),
                password
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
}
