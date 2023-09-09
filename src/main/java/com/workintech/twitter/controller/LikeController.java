package com.workintech.twitter.controller;


import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.mapping.LikeResponse;
import com.workintech.twitter.service.LikeService;
import com.workintech.twitter.service.ReplyService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet/like")
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<LikeResponse> createLike(@PathVariable int tweetId, @RequestBody int userId) {
        Tweet foundTweet= tweetService.findById(tweetId);
        User foundUser = userService.findUserById(userId);
        LikeResponse likeResponse = new LikeResponse();

        likeResponse.setTweetId(foundTweet.getId());
        likeResponse.setUserId(foundUser.getId());


        likeService.likeTweet(foundTweet.getId(),foundUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponse);
    }


    @DeleteMapping("/{id}")
    public void unlike(@PathVariable int id) {
        Like like = likeService.findLikeById(id);

        if(like != null) {
            likeService.unlikeTweet(id);

        }

    }
}
