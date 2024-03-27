package com.springapps.redditcloneapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postName;
    private String description;

    private Long voteCount;
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name="subreddit_id")
    @JsonBackReference("subreddit-post")
    private Subreddit subreddit;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-post")
    private User user;
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("post-comment")
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("post-vote")
    private List<Vote> voteList;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Vote> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }
}
