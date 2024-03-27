package com.springapps.redditcloneapp.repository;

import com.springapps.redditcloneapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findByPost_IdAndUser_Id(Long postId, Long userId);
}
