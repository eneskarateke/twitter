package com.workintech.twitter.controller;

import com.workintech.twitter.dto.TweetRequest;
import com.workintech.twitter.entity.*;
import com.workintech.twitter.mapping.LikeResponse;
import com.workintech.twitter.mapping.ReplyResponse;
import com.workintech.twitter.mapping.RetweetResponse;
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
            tweetResponse.setEmail(tweet.getUser().getEmail());


            List<LikeResponse> likeResponses = new ArrayList<>();
            for (Like like : tweet.getLikes()) {
                LikeResponse likeResponse = new LikeResponse();
                likeResponse.setTweetId(tweet.getId());
                likeResponse.setLikerId(like.getLiker().getId());
                likeResponse.setLikerEmail(like.getLiker().getEmail());
                likeResponse.setLikeId(like.getId());
                likeResponses.add(likeResponse);
            }
            tweetResponse.setLikes(likeResponses);

            List<RetweetResponse> retweetResponses = new ArrayList<>();
            for (Retweet retweet : tweet.getRetweets()) {
                RetweetResponse retweetResponse = new RetweetResponse();
                retweetResponse.setTweetId(tweet.getId());
                retweetResponse.setUserId(retweet.getUser().getId());
                retweetResponse.setEmail(retweet.getUser().getEmail());
                retweetResponse.setMessage("Successful retrive");

                retweetResponses.add(retweetResponse);
            }
            tweetResponse.setRetweets(retweetResponses);

            List<ReplyResponse> replyResponses = new ArrayList<>();
            for (Reply reply : tweet.getReplies()) {
                ReplyResponse replyResponse = new ReplyResponse();
                replyResponse.setTweetId(tweet.getId());
                replyResponse.setReplierId(reply.getReplier().getId());
                replyResponse.setPost(reply.getPost());
                replyResponse.setEmail(reply.getReplier().getEmail());
                replyResponse.setReplyId(reply.getId());

                replyResponses.add(replyResponse);
            }
            tweetResponse.setReplies(replyResponses);

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
        tweetResponse.setEmail(foundTweet.getUser().getEmail());

        List<LikeResponse> likeResponses = new ArrayList<>();
        for (Like like : foundTweet.getLikes()) {
            LikeResponse likeResponse = new LikeResponse();
            likeResponse.setTweetId(foundTweet.getId());
            likeResponse.setLikerId(like.getLiker().getId());
            likeResponse.setLikerEmail(like.getLiker().getEmail());
            likeResponses.add(likeResponse);
            likeResponse.setLikeId(like.getId());
        }
        tweetResponse.setLikes(likeResponses);

        List<RetweetResponse> retweetResponses = new ArrayList<>();
        for (Retweet retweet : foundTweet.getRetweets()) {
            RetweetResponse retweetResponse = new RetweetResponse();
            retweetResponse.setTweetId(retweet.getTweet().getId());
            retweetResponse.setUserId(retweet.getUser().getId());
            retweetResponse.setEmail(retweet.getUser().getEmail());
            retweetResponse.setMessage("Successful retrieve");

            retweetResponses.add(retweetResponse);
        }
        tweetResponse.setRetweets(retweetResponses);


        List<ReplyResponse> replyResponses = new ArrayList<>();
        for (Reply reply : foundTweet.getReplies()) {
            ReplyResponse replyResponse = new ReplyResponse();
            replyResponse.setTweetId(foundTweet.getId());
            replyResponse.setReplierId(reply.getReplier().getId());
            replyResponse.setPost(reply.getPost());
            replyResponse.setEmail(reply.getReplier().getEmail());
            replyResponse.setReplyId(reply.getId());

            replyResponses.add(replyResponse);
        }
        tweetResponse.setReplies(replyResponses);

        return ResponseEntity.status(HttpStatus.OK).body(tweetResponse);
    }

    @PostMapping("/")
    public ResponseEntity<TweetResponse> createTweet(@Validated @RequestBody TweetRequest tweetRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User foundUser = userService.findUserByEmail(userEmail);


            Tweet newTweet = new Tweet();
            newTweet.setUser(foundUser);
            newTweet.setPost(tweetRequest.getTweet());

            Tweet createdTweet = tweetService.save(newTweet);

            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setId(createdTweet.getId());
            tweetResponse.setUserId(foundUser.getId());
            tweetResponse.setPost(createdTweet.getPost());
        tweetResponse.setEmail(foundUser.getEmail());


        return ResponseEntity.status(HttpStatus.CREATED).body(tweetResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TweetResponse> updateTweet(@PathVariable int id, @Validated @RequestBody TweetRequest updatedTweet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Tweet foundTweet = tweetService.findById(id);

        if (foundTweet.getUser().getEmail().equals(userEmail)) {
            foundTweet.setPost(updatedTweet.getTweet());

            Tweet updated = tweetService.update(foundTweet.getId(), foundTweet);

            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setId(updated.getId());
            tweetResponse.setPost(updated.getPost());
            tweetResponse.setUserId(foundTweet.getUser().getId());
            tweetResponse.setEmail(foundTweet.getUser().getEmail());

            List<LikeResponse> likeResponses = new ArrayList<>();
            for (Like like : foundTweet.getLikes()) {
                LikeResponse likeResponse = new LikeResponse();
                likeResponse.setTweetId(updated.getId());
                likeResponse.setLikerId(like.getLiker().getId());
                likeResponse.setLikerEmail(like.getLiker().getEmail());
                likeResponse.setLikeId(like.getId());
                likeResponses.add(likeResponse);
            }
            tweetResponse.setLikes(likeResponses);

            List<RetweetResponse> retweetResponses = new ArrayList<>();
            for (Retweet retweet : foundTweet.getRetweets()) {
                RetweetResponse retweetResponse = new RetweetResponse();
                retweetResponse.setTweetId(updated.getId());
                retweetResponse.setUserId(retweet.getUser().getId());
                retweetResponse.setEmail(retweet.getUser().getEmail());
                retweetResponse.setMessage("Successful retrive");


                retweetResponses.add(retweetResponse);
            }
            tweetResponse.setRetweets(retweetResponses);


            List<ReplyResponse> replyResponses = new ArrayList<>();
            for (Reply reply : foundTweet.getReplies()) {
                ReplyResponse replyResponse = new ReplyResponse();
                replyResponse.setTweetId(foundTweet.getId());
                replyResponse.setReplierId(reply.getReplier().getId());
                replyResponse.setPost(reply.getPost());
                replyResponse.setEmail(reply.getReplier().getEmail());
                replyResponse.setReplyId(reply.getId());

                replyResponses.add(replyResponse);
            }
            tweetResponse.setReplies(replyResponses);

            return ResponseEntity.status(HttpStatus.CREATED).body(tweetResponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Tweet foundTweet = tweetService.findById(id);

        if (foundTweet.getUser().getEmail().equals(userEmail)) {
            tweetService.delete(foundTweet.getId());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
