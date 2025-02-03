package com.paulfloresdev.tweetsappbackend.Repository;

import com.paulfloresdev.tweetsappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
}
