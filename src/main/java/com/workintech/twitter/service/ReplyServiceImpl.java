package com.workintech.twitter.service;

import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.exceptions.TwitterException;
import com.workintech.twitter.repository.ReplyRepository;
import com.workintech.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {

    private ReplyRepository replyRepository;

    private TweetRepository tweetRepository;
@Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository, TweetRepository tweetRepository) {
        this.replyRepository = replyRepository;
        this.tweetRepository = tweetRepository;
    }
    @Override
    public Reply findReplyById(int replyId) {
    Optional<Reply> foundReply = replyRepository.findById(replyId);

    if (foundReply.isPresent()) {
        return foundReply.get();
    }
    throw new TwitterException("Reply with given id is not valid: " + replyId, HttpStatus.NOT_FOUND);
    }

    @Override
    public void replyToTweet(int tweetId, Reply reply) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        if (tweet.isPresent()) {
            reply.setTweet(tweet.get());
            replyRepository.save(reply);
        }
    }

    @Override
    public void deleteReply(int replyId) {
    Optional<Reply> reply = replyRepository.findById(replyId);
    if(reply.isPresent()) {
        replyRepository.deleteById(replyId);

    }
    }
}
