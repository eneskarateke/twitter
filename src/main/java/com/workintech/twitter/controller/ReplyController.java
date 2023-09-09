package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.ReplyService;
import com.workintech.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet/reply")
public class ReplyController {


    @Autowired
    private ReplyService replyService;
    @Autowired
    private TweetService tweetService;


    @PostMapping("/{id}")
    public Reply createReply(@PathVariable int id, @RequestBody String post) {
        Tweet foundTweet= tweetService.findById(id);
        Reply reply = new Reply();

        if(foundTweet !=null){
            reply.setTweet(foundTweet);
            reply.setPost(post);
            replyService.replyToTweet(reply.getTweet().getId(),reply);
        }
        //TODO throw exception
         return null;
    }


    @DeleteMapping("/{id}")
    public void createReply(@PathVariable int id) {
        Reply reply = replyService.findReplyById(id);

        if(reply != null) {
            replyService.deleteReply(id);

        }

    }
}
