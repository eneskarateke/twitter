package com.workintech.twitter.controller;

import com.workintech.twitter.dto.UserInfoRequest;
import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.mapping.RetweetResponse;
import com.workintech.twitter.service.RetweetService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet/retweet")
public class RetweetController {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private RetweetService retweetService;

    @Autowired
    private UserService userService;



    @PostMapping("/{id}")
    public ResponseEntity<RetweetResponse> createRetweet(@PathVariable int id, @RequestBody UserInfoRequest userInfo) {
        Tweet foundTweet= tweetService.findById(id);
        User foundUser= userService.findUserById(userInfo.getUserId());

        RetweetResponse retweetResponse = new RetweetResponse();
        retweetResponse.setMessage("Retweet edildi!");
        retweetResponse.setTweetId(foundTweet.getId());
        retweetResponse.setUserId(foundUser.getId());
        Retweet retweet = new Retweet();

            retweet.setTweet(foundTweet);


                retweet.setUser(foundUser);
                retweetService.retweet(foundTweet.getId(), foundUser.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(retweetResponse);


    }
}
