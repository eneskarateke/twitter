package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tweet", schema = "twitter")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "post")
    @NotNull
    @Size(max = 280, message = "Tweet content cannot exceed 280 characters")
    private String post;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Reply> replies;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Retweet> retweets;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;


}
