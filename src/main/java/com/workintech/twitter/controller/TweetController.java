package com.workintech.twitter.controller;

import com.workintech.twitter.dto.TweetRequest;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exceptions.TwitterException;
import com.workintech.twitter.mapping.TweetResponse;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<TweetResponse> getAllTweets() {
        List<Tweet> tweets = tweetService.findAll();

        List<TweetResponse> tweetResponses = new ArrayList<>();

        for (Tweet tweet : tweets) {
            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setId(tweet.getId());
            tweetResponse.setPost(tweet.getPost());
            tweetResponse.setUserId(tweet.getUser().getId());


            tweetResponses.add(tweetResponse);
        }

        return tweetResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable int id) {

        Tweet foundTweet = tweetService.findById(id);

        TweetResponse tweetResponse = new TweetResponse();

        tweetResponse.setId(id);
        tweetResponse.setPost(foundTweet.getPost());
        tweetResponse.setUserId(foundTweet.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(tweetResponse);
    }

    @PostMapping("/")
    public ResponseEntity<TweetResponse> createTweet(@Validated @RequestBody TweetRequest tweetRequest) {
        User foundUser = userService.findUserById(tweetRequest.getUserId());

        // Create a new tweet
        Tweet newTweet = new Tweet();
        newTweet.setUser(foundUser);
        newTweet.setPost(tweetRequest.getTweet());


        Tweet createdTweet = tweetService.save(newTweet);


        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setId(createdTweet.getId());

        tweetResponse.setUserId(foundUser.getId());

        tweetResponse.setPost(createdTweet.getPost());


        return ResponseEntity.status(HttpStatus.CREATED).body(tweetResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetResponse> updateTweet(@PathVariable int id, @Validated @RequestBody TweetRequest updatedTweet) {
        Tweet foundTweet = tweetService.findById(id);


        foundTweet.setPost(updatedTweet.getTweet());
        foundTweet.setId(id);
        foundTweet.setUser(foundTweet.getUser());


        Tweet updated = tweetService.update(foundTweet.getId(), foundTweet);

        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setId(updated.getId());
        tweetResponse.setPost(updated.getPost());
        tweetResponse.setUserId(foundTweet.getUser().getId());


        return ResponseEntity.status(HttpStatus.CREATED).body(tweetResponse);
    }


    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable int id) {
        Tweet foundTweet= tweetService.findById(id);

        tweetService.delete(foundTweet.getId());
    }
}
