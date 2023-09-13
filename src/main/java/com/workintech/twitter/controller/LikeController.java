package com.workintech.twitter.controller;


import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.mapping.LikeResponse;
import com.workintech.twitter.service.LikeService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<LikeResponse> createLike(@PathVariable int tweetId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();


        Tweet foundTweet= tweetService.findById(tweetId);
        User foundUser = userService.findUserByEmail(userEmail);

            LikeResponse likeResponse = new LikeResponse();

            likeResponse.setTweetId(foundTweet.getId());
            likeResponse.setLikerId(foundUser.getId());
            likeResponse.setLikerEmail(foundUser.getEmail());
            likeService.likeTweet(foundTweet.getId(),foundUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(likeResponse);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unlike(@PathVariable int id) {
        Like like = likeService.findLikeById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();


        if (like.getLiker().getEmail().equals(userEmail)) {
            likeService.unlikeTweet(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


    }
}
