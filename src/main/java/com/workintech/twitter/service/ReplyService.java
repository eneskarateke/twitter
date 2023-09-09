package com.workintech.twitter.service;

import com.workintech.twitter.entity.Reply;
import com.workintech.twitter.entity.Tweet;

import java.util.List;

public interface ReplyService {
    void replyToTweet(int tweetId, Reply reply);
    void deleteReply(int replyId);
    public Reply findReplyById(int replyId);
}
