package com.paulfloresdev.tweetsappbackend.services;

import com.paulfloresdev.tweetsappbackend.DAO.User.*;
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


    /*
        Register a user
     */
    public ResponseEntity<ResponseDAO> registerUser(Optional<UserRequestDAO> user) {

        // Check if the user is empty and if the user already exists
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDAO("You must provide an email, names and a password"));
        if (isUserExist(user.get().getEmail())) return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDAO("User already exists"));

        // Create a new userResponse with the user data
        UserResponseDAO userResponse = new UserResponseDAO(
                user.get().getEmail(),
                user.get().getNames(),
                user.get().getPassword()
        );

        // Save the user in the database
       this.usersRepository.save(userResponse.getUser());

       // Check if the user was saved
       Optional<User> userFromDB = this.usersRepository.findById(user.get().getEmail());

       // Return the userResponse if the user was saved, else return an internal server error
       return userFromDB.isPresent()
               ? ResponseEntity.ok(userResponse)
               : ResponseEntity.internalServerError().body(new ErrorResponseDAO("User cannot be registered"));
    }

    /*
        Authenticate a user
     */
    public ResponseEntity<ResponseDAO> authenticateUser(Optional<UserAuthenticateDAO> user) {

        // Check if the user is empty
        if (user.isPresent()) {
            // Get the user from the database
            Optional<User> userFromDB = this.usersRepository.findById(user.get().getEmail());

            // Check if the user exists and if the password is correct
            if (userFromDB.isPresent()) {

                if ( userFromDB.get().getPassword().equals(user.get().getPassword()) ) {
                    UserResponseDAO userResponse = new UserResponseDAO(
                            userFromDB.get().getEmail(),
                            userFromDB.get().getNames(),
                            userFromDB.get().getPassword()
                    );
                    return ResponseEntity.ok(userResponse);
                } else {
                    // Return unauthorized if the password is incorrect
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDAO("Password is incorrect"));
                }
            } else {
                // Return not found if the user does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDAO("User not found"));
            }
        }
        // Return bad request if the user is empty
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDAO("You must provide an email and a password"));
    }

    /*
        Get a user by email
     */
    public Optional<User> getUser(String email) {
        return this.usersRepository.findById(email);
    }

    /*
        Check if a user exists
     */
    public boolean isUserExist(String email) {
        Optional<User> userOptional = this.usersRepository.findById(email);
        return userOptional.isPresent();
    }

    /*
        Get all users
     */
    public List<User> getAllUsers() {
        return this.usersRepository.findAll();
    }
}
