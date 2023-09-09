package com.workintech.twitter.service;

import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.repository.ReplyRepository;
import com.workintech.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    //TODO
        return null;
    }

    @Override
    public void replyToTweet(int tweetId, Reply reply) {
        Tweet tweet = tweetRepository.findById(tweetId).orElse(null);
        if (tweet != null) {
            reply.setTweet(tweet);
            replyRepository.save(reply);
        }
    }

    @Override
    public void deleteReply(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
