package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;


public interface RetweetService {
    void retweet(int tweetId, User user);

}
