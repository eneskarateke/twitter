package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;

import java.util.List;

public interface LikeService {
    void likeTweet(int tweetId, int userId);
    void unlikeTweet(int likeId);
    public Like findLikeById(int likeId);

}
