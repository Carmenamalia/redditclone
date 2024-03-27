package com.springapps.redditcloneapp.model;

public enum VoteType {

    UPVOTE(+1L),
    DOWNVOTE(-1L);

    private final Long value;

    VoteType(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

}
