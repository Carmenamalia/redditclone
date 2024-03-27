package com.springapps.redditcloneapp.service;

import com.springapps.redditcloneapp.dto.SubredditRequestDTO;
import com.springapps.redditcloneapp.dto.mapper.SubredditMapper;
import com.springapps.redditcloneapp.model.Subreddit;
import com.springapps.redditcloneapp.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubredditService {

    private SubredditRepository subredditRepository;
    private SubredditMapper subredditMapper;

    @Autowired
    public SubredditService(SubredditRepository subredditRepository, SubredditMapper subredditMapper) {
        this.subredditRepository = subredditRepository;
        this.subredditMapper = subredditMapper;
    }

    @Transactional
    public Subreddit createSubreddit(SubredditRequestDTO subredditRequestDTO) {
        Subreddit subreddit = subredditMapper.mapSubredditRequestDTOtoSubreddit(subredditRequestDTO);
        return subredditRepository.save(subreddit);
    }
}
