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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Tweet foundTweet= tweetService.findById(id);
        User replier = userService.findUserByEmail(userEmail);
        Reply reply = new Reply();


        reply.setTweet(foundTweet);
        reply.setPost(replyRequest.getTweet());
        reply.setReplier(replier);
        replyService.replyToTweet(foundTweet.getId(), reply);


            ReplyResponse replyResponse=new ReplyResponse();
            replyResponse.setTweetId(foundTweet.getId());
            replyResponse.setReplierId(replier.getId());
            replyResponse.setPost(replyRequest.getTweet());
            return ResponseEntity.status(HttpStatus.CREATED).body(replyResponse);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable int id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Reply reply = replyService.findReplyById(id);
        if (reply.getReplier().getEmail().equals(userEmail)) {
            replyService.deleteReply(reply.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
