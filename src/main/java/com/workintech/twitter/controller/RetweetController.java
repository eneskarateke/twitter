package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.RetweetService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createRetweet(@PathVariable int id, @RequestBody int userId) {
        Tweet foundTweet= tweetService.findById(id);
        User foundUser= userService.findUserById(userId);
        Retweet retweet = new Retweet();

        if(foundTweet !=null){
            retweet.setTweet(foundTweet);
            if(foundUser!=null) {
                retweet.setUser(foundUser);
                retweetService.retweet(foundTweet.getId(), foundUser.getId());
            }
        }
    }
}
