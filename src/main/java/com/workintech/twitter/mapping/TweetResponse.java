package com.workintech.twitter.mapping;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetResponse {
    private int id;
    private String post;
    private int userId;
    private List<LikeResponse> likes;
    private List<ReplyResponse> replies;
    private List<RetweetResponse> retweets;
    private String email;



}
