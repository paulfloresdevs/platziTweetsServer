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


    /*
        Register a user
     */
    public ResponseEntity<UserResponseDAO> registerUser(Optional<UserRequestDAO> user) {

        // Check if the user is empty and if the user already exists
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (isUserExist(user.get().getEmail())) return ResponseEntity.status(HttpStatus.CONFLICT).build();

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
               : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /*
        Authenticate a user
     */
    public ResponseEntity<UserResponseDAO> authenticateUser(Optional<UserAuthenticateDAO> user) {

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
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                // Return not found if the user does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        // Return bad request if the user is empty
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
