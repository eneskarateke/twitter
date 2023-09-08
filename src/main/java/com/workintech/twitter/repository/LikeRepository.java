package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query("SELECT l FROM Like l WHERE l.tweet.id = :tweetId AND l.user.id = :userId")
    Optional<Like> findByTweetIdAndUserId(int tweetId, int userId);
}
