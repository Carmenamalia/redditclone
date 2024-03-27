package com.springapps.redditcloneapp.service;

import com.springapps.redditcloneapp.dto.VoteRequestDTO;
import com.springapps.redditcloneapp.dto.VoteResponseDTO;
import com.springapps.redditcloneapp.model.Post;
import com.springapps.redditcloneapp.model.User;
import com.springapps.redditcloneapp.model.Vote;
import com.springapps.redditcloneapp.repository.PostRepository;
import com.springapps.redditcloneapp.repository.UserRepository;
import com.springapps.redditcloneapp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private VoteRepository voteRepository;

    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, PostRepository postRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public VoteResponseDTO addVote(VoteRequestDTO voteRequestDTO) {
        User user = userRepository.findById(voteRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Post post = postRepository.findById(voteRequestDTO.getPostId()).orElseThrow(() -> new RuntimeException("post not found"));
        //daca userul are un vot la post deja
        //sterg votul
        //scad din vote count valoarea tipului votului
        //altfel
        //fac un vote nou
        //il leg de user si post
        //ii setez count-ul in functie de tip
        //ii setez tipul
        VoteResponseDTO voteResponseDTO = new VoteResponseDTO();
        Optional<Vote> voteOptional = voteRepository.findByPost_IdAndUser_Id(voteRequestDTO.getPostId(), voteRequestDTO.getUserId());
        if (voteOptional.isPresent()) {
            post.setVoteCount(post.getVoteCount() - voteOptional.get().getVoteType().getValue());
            postRepository.save(post);
            voteRepository.delete(voteOptional.get());
            voteResponseDTO.setDeleteMessage("already a vote " + voteOptional.get() + " so the vote was delete");
            //throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "already a vote "+ voteOptional.get()+" so the vote was deleted");
        } else {
            Vote vote = new Vote();
            vote.setPost(post);
            vote.setUser(user);
            post.setVoteCount(post.getVoteCount() + voteRequestDTO.getVoteType().getValue());
            vote.setVoteType(voteRequestDTO.getVoteType());
            postRepository.save(post);
            voteRepository.save(vote);
            voteResponseDTO.setVoteType(vote.getVoteType());
        }
        return voteResponseDTO;
    }

}
