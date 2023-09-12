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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            tweetResponse.setLikes(tweet.getLikes().size());
            tweetResponse.setReplies(tweet.getReplies().size());
            tweetResponse.setRetweets(tweet.getRetweets().size());


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
        tweetResponse.setLikes(foundTweet.getLikes().size());
        tweetResponse.setReplies(foundTweet.getReplies().size());
        tweetResponse.setRetweets(foundTweet.getRetweets().size());
        return ResponseEntity.status(HttpStatus.OK).body(tweetResponse);
    }

    @PostMapping("/")
    public ResponseEntity<TweetResponse> createTweet(@Validated @RequestBody TweetRequest tweetRequest) {
        // Get the currently authenticated user's email from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // This should contain the email from the token

        // Retrieve the user based on the email
        User foundUser = userService.findUserByEmail(userEmail);

        if (foundUser != null) {
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
        } else {
            // Handle the case where the user is not found
            throw new TwitterException("User not found with email: " + userEmail,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<TweetResponse> updateTweet(@PathVariable int id, @Validated @RequestBody TweetRequest updatedTweet) {
        // Get the currently authenticated user's email from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // This should contain the email from the token

        // Retrieve the tweet to be updated
        Tweet foundTweet = tweetService.findById(id);

        // Check if the user making the request is the same as the tweet's user
        if (foundTweet.getUser().getEmail().equals(userEmail)) {
            foundTweet.setPost(updatedTweet.getTweet());

            // Update the tweet
            Tweet updated = tweetService.update(foundTweet.getId(), foundTweet);

            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setId(updated.getId());
            tweetResponse.setPost(updated.getPost());
            tweetResponse.setUserId(foundTweet.getUser().getId());
            tweetResponse.setLikes(foundTweet.getLikes().size());
            tweetResponse.setReplies(foundTweet.getReplies().size());
            tweetResponse.setRetweets(foundTweet.getRetweets().size());

            return ResponseEntity.status(HttpStatus.CREATED).body(tweetResponse);
        } else {
            // User is not authorized to update this tweet
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable int id) {
        // Get the currently authenticated user's email from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // This should contain the email from the token

        // Retrieve the tweet to be deleted
        Tweet foundTweet = tweetService.findById(id);

        // Check if the user making the request is the same as the tweet's user
        if (foundTweet.getUser().getEmail().equals(userEmail)) {
            // Delete the tweet
            tweetService.delete(foundTweet.getId());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            // User is not authorized to delete this tweet
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
