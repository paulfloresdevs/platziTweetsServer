package com.paulfloresdev.tweetsappbackend.services;

import com.paulfloresdev.tweetsappbackend.DAO.User.UserDAO;
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

    public ResponseEntity<UserResponseDAO> registerUser(Optional<User> user) {
        if (user.isPresent()) {
           this.usersRepository.save(user.get());
           Optional<User> userFromDB = this.usersRepository.findById(user.get().getId());

           if (userFromDB.isPresent()) {
                User userFromDBNonOptional = userFromDB.get();

               UserResponseDAO userResponse = new UserResponseDAO(
                       userFromDBNonOptional.getEmail(),
                       userFromDBNonOptional.getFullName()
               );

               return ResponseEntity.ok(userResponse);
           } else {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
           }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<UserResponseDAO> authenticateUser(Optional<UserDAO> user) {
        if (user.isPresent()) {
            Optional<User> userFromDB = this.usersRepository.findByEmail(user.get().getEmail());

            if (userFromDB.isPresent()) {

                if ( userFromDB.get().getPassword().equals(user.get().getPassword()) ) {
                    UserResponseDAO userResponse = new UserResponseDAO(
                            userFromDB.get().getEmail(),
                            userFromDB.get().getFullName()
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

    public Optional<User> getUser(Long id) {
        return this.usersRepository.findById( id);
    }

    public boolean isUserExist(Long id) {
        Optional<User> userOptional = this.usersRepository.findById(id);
        return userOptional.isPresent();
    }

    public List<User> getAllUsers() {
        return this.usersRepository.findAll();
    }
}
