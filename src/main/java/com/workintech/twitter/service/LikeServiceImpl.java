package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {


    private LikeRepository likeRepository;
    private TweetRepository tweetRepository;
    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, TweetRepository tweetRepository) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public void likeTweet(int tweetId, User user) {
        Tweet tweet = tweetRepository.findById(tweetId).orElse(null);
        if (tweet != null) {
            Like like = new Like();
            like.setTweet(tweet);
            like.setUser(user);
            likeRepository.save(like);
        }
    }

    @Override
    public void unlikeTweet(int tweetId, User user) {
        Like like = likeRepository.findByTweetIdAndUserId(tweetId, user.getId()).orElse(null);
        if (like != null) {
            likeRepository.delete(like);
        }
    }
}