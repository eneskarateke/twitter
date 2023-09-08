package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;

import java.util.List;

public interface LikeService {
    void likeTweet(int tweetId, User user);
    void unlikeTweet(int tweetId, User user);
}
