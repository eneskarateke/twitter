package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.RetweetRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetweetServiceImpl implements RetweetService {

    @Autowired
    private RetweetRepository retweetRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void retweet(int tweetId, User user) {
        Tweet tweet = tweetRepository.findById(tweetId).orElse(null);
        User retweetingUser = userRepository.findById(user.getId()).orElse(null);
        if (tweet != null && retweetingUser != null) {
            Retweet retweet = new Retweet();
            retweet.setTweet(tweet);
            retweet.setUser(retweetingUser);
            retweetRepository.save(retweet);
        }
    }
}

