package com.workintech.twitter.controller;


import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.LikeService;
import com.workintech.twitter.service.ReplyService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Like createLike(@PathVariable int tweetId, @RequestBody int userId) {
        Tweet foundTweet= tweetService.findById(tweetId);
        User foundUser = userService.findUserById(userId);
        Like like = new Like();

        if(foundTweet !=null){
            like.setTweet(foundTweet);
            if(foundUser!=null) {
                like.setUser(foundUser);
                likeService.likeTweet(like.getId(),like.getUser().getId());
            }

        }
        //TODO throw exception
        return null;
    }


    @DeleteMapping("/{id}")
    public void unlike(@PathVariable int id) {
        Like like = likeService.findLikeById(id);

        if(like != null) {
            likeService.unlikeTweet(id);

        }

    }
}
