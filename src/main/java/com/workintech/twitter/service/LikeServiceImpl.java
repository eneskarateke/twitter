package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exceptions.TwitterException;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {


    private LikeRepository likeRepository;
    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;

    }
    @Override
    public Like findLikeById(int likeId) {
        Optional<Like> foundLike = likeRepository.findById(likeId);

        if (foundLike.isPresent()) {
            return foundLike.get();
        }
        throw new TwitterException("Like with given id does not exits: " + likeId , HttpStatus.NOT_FOUND);

    }

    @Override
    public void likeTweet(int tweetId, int userId) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        Optional<User> user = userRepository.findById(userId);

        if (tweet.isPresent()) {
            Like like = new Like();
            like.setTweet(tweet.get());
            if(user.isPresent()) {
                like.setUser(user.get());
                likeRepository.save(like);

            }
        }
    }

    @Override
    public void unlikeTweet(int likeId) {
        Like like = likeRepository.findById(likeId).orElse(null);
        if (like != null) {
            likeRepository.delete(like);
        }
    }
}