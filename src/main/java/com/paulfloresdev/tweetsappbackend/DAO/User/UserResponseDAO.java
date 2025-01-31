package com.paulfloresdev.tweetsappbackend.DAO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDAO {
    private String email;
    private String names;
    private String nickname;

    public UserResponseDAO(String email, String names) {
        this.email = email;
        this.names = names;
        NicknameConstructor(email, names);
    }

    private void NicknameConstructor(String email, String names){

        StringBuilder nicknameBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                nicknameBuilder.append(email.charAt(i));
            } else {
                nicknameBuilder.append(names.charAt(i));
            }
        }
        this.nickname = nicknameBuilder.toString();
    }
}
