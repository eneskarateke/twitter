package com.workintech.twitter.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetweetResponse {
    private int tweetId;
    private int userId;
    private String message;
}

