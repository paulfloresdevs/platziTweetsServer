package com.paulfloresdev.tweetsappbackend.services;

import com.paulfloresdev.tweetsappbackend.DAO.User.UserAuthenticateDAO;
import com.paulfloresdev.tweetsappbackend.DAO.User.UserRequestDAO;
import com.paulfloresdev.tweetsappbackend.DAO.User.UserResponseDAO;
import com.paulfloresdev.tweetsappbackend.models.User;
import com.paulfloresdev.tweetsappbackend.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public ResponseEntity<UserResponseDAO> registerUser(Optional<UserRequestDAO> user) {

        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (isUserExist(user.get().getEmail())) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        UserResponseDAO userResponse = new UserResponseDAO(
                user.get().getEmail(),
                user.get().getNames(),
                user.get().getPassword()
        );

       this.usersRepository.save(userResponse.getUser());

       Optional<User> userFromDB = this.usersRepository.findById(user.get().getEmail());

       return userFromDB.isPresent()
               ? ResponseEntity.ok(userResponse)
               : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public ResponseEntity<UserResponseDAO> authenticateUser(Optional<UserAuthenticateDAO> user) {
        if (user.isPresent()) {
            Optional<User> userFromDB = this.usersRepository.findById(user.get().getEmail());

            if (userFromDB.isPresent()) {

                if ( userFromDB.get().getPassword().equals(user.get().getPassword()) ) {
                    UserResponseDAO userResponse = new UserResponseDAO(
                            userFromDB.get().getEmail(),
                            userFromDB.get().getNames(),
                            userFromDB.get().getPassword()
                    );
                    return ResponseEntity.ok(userResponse);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public Optional<User> getUser(String email) {
        return this.usersRepository.findById(email);
    }

    public boolean isUserExist(String email) {
        Optional<User> userOptional = this.usersRepository.findById(email);
        return userOptional.isPresent();
    }

    public List<User> getAllUsers() {
        return this.usersRepository.findAll();
    }
}
