package com.springapps.redditcloneapp.service;

import com.springapps.redditcloneapp.dto.PostRequestDTO;
import com.springapps.redditcloneapp.dto.mapper.PostMapper;
import com.springapps.redditcloneapp.model.Post;
import com.springapps.redditcloneapp.model.Subreddit;
import com.springapps.redditcloneapp.model.User;
import com.springapps.redditcloneapp.repository.PostRepository;
import com.springapps.redditcloneapp.repository.SubredditRepository;
import com.springapps.redditcloneapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private SubredditRepository subredditRepository;
    private PostMapper postMapper;

    @Autowired
    public PostService(UserRepository userRepository, PostRepository postRepository, SubredditRepository subredditRepository, PostMapper postMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
        this.postMapper = postMapper;
    }

    @Transactional
    public Post createPost(PostRequestDTO postRequestDTO){
        Post post = postMapper.mapPostRequestDTOtoPost(postRequestDTO);
        User user = userRepository.findById(postRequestDTO.getUserId()).orElseThrow(()->new RuntimeException("user not found"));
        Subreddit subreddit = subredditRepository.findById(postRequestDTO.getSubredditId()).orElseThrow(()->new RuntimeException("subreddit not found"));
        post.setSubreddit(subreddit);
        post.setUser(user);
        return postRepository.save(post);
    }
}
