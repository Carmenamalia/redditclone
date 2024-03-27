package com.springapps.redditcloneapp.controller;

import com.springapps.redditcloneapp.dto.SubredditRequestDTO;
import com.springapps.redditcloneapp.model.Subreddit;
import com.springapps.redditcloneapp.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subreddit")
public class SubredditController {

    private SubredditService subredditService;

    @Autowired
    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;

    }

    @PostMapping
    public ResponseEntity<Subreddit> createSubreddit(@RequestBody SubredditRequestDTO subredditRequestDTO) {
        Subreddit subreddit = subredditService.createSubreddit(subredditRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(subreddit);
    }
}
