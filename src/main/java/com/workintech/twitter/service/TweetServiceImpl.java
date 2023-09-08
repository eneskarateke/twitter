package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TweetServiceImpl implements TweetService{

    private TweetRepository tweetRepository;
@Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet findById(int id) {
    Optional<Tweet> tweet= tweetRepository.findById(id);

    if(tweet.isPresent()) {
    return tweet.get();
    }
        //TODO EXCEPTION
    return null;
    }

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet update(int tweetId, Tweet tweet) {
        Tweet existingTweet = tweetRepository.findById(tweetId).orElse(null);
        if (existingTweet != null) {
            existingTweet.setPost(tweet.getPost());
            existingTweet.setId(tweetId);
            return tweetRepository.save(existingTweet);
        }
        return null; // Tweet not found
    }

    @Override
    public Tweet delete(int id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isPresent()){
            tweetRepository.delete(tweet.get());
            return tweet.get();
        }
        //TODO throw Exception
        return null;
    }
}