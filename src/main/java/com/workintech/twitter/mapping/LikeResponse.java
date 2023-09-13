package com.workintech.twitter.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private int likeId;
    private int tweetId;
    private int likerId;
    private String likerEmail;
}
