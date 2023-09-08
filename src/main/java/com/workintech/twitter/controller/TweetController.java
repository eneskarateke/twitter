package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @GetMapping("/")
    public List<Tweet> getAllTweets() {
        return tweetService.findAll();
    }

    @GetMapping("/{id}")
    public Tweet getTweetById(@PathVariable int id) {
        return tweetService.findById(id);
    }

    @PostMapping("/")
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.save(tweet);
    }

    @PutMapping("/{id}")
    public Tweet updateTweet(@PathVariable int id, @RequestBody Tweet tweet) {
        return tweetService.update(id, tweet);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable int id) {
        tweetService.delete(id);
    }
}
