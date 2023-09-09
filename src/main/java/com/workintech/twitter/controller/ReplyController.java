package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.ReplyService;
import com.workintech.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet/reply")
public class ReplyController {


    @Autowired
    private ReplyService replyService;
    @Autowired
    private TweetService tweetService;


    @PostMapping("/{id}")
    public Reply createReply(@PathVariable int id, @Validated  @RequestBody String post) {
        Tweet foundTweet= tweetService.findById(id);
        Reply reply = new Reply();


            reply.setTweet(foundTweet);
            reply.setPost(post);
            replyService.replyToTweet(reply.getTweet().getId(),reply);
            return reply;

    }


    @DeleteMapping("/{id}")
    public void deleteReply(@PathVariable int id) {
        Reply reply = replyService.findReplyById(id);

        if(reply != null) {
            replyService.deleteReply(id);

        }

    }
}
