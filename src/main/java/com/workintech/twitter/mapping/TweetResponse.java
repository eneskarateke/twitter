package com.workintech.twitter.mapping;

import com.workintech.twitter.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetResponse {
    private int id;
    private String post;
    private int userId;
    private int likes;
    private int replies;
    private int retweets;



}
