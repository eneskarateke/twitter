package com.workintech.twitter.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {
    private int replyId;
    private int tweetId;
    private int replierId;
    private String post;
    private String email;
}
