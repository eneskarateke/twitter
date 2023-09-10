package com.workintech.twitter.controller;

import com.workintech.twitter.dto.ReplyRequest;
import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.mapping.ReplyResponse;
import com.workintech.twitter.service.ReplyService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet/reply")
public class ReplyController {


    @Autowired
    private ReplyService replyService;
    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;



    @PostMapping("/{id}")
    public ResponseEntity<ReplyResponse> createReply(@PathVariable int id, @Validated  @RequestBody ReplyRequest replyRequest) {
        Tweet foundTweet= tweetService.findById(id);
        User replier = userService.findUserById(replyRequest.getUserId());
        Reply reply = new Reply();


            reply.setTweet(foundTweet);
            reply.setPost(replyRequest.getTweet());
            reply.setReplier(replier);
            replyService.replyToTweet(reply.getTweet().getId(),reply);


            ReplyResponse replyResponse=new ReplyResponse();
            replyResponse.setTweetId(foundTweet.getId());
            replyResponse.setReplierId(replyRequest.getUserId());
            replyResponse.setPost(replyRequest.getTweet());
            return ResponseEntity.status(HttpStatus.CREATED).body(replyResponse);

    }


    @DeleteMapping("/{id}")
    public void deleteReply(@PathVariable int id) {
        Reply reply = replyService.findReplyById(id);

        replyService.deleteReply(id);

    }
}
