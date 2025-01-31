package com.paulfloresdev.tweetsappbackend.Repository;

import com.paulfloresdev.tweetsappbackend.models.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TweetsRepository extends JpaRepository<Tweet, Long> {

}
