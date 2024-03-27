package com.springapps.redditcloneapp.repository;

import com.springapps.redditcloneapp.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
}
