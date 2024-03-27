package com.springapps.redditcloneapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springapps.redditcloneapp.dto.CommentRequestDTO;
import com.springapps.redditcloneapp.dto.PostRequestDTO;
import com.springapps.redditcloneapp.model.Comment;
import com.springapps.redditcloneapp.model.Post;
import com.springapps.redditcloneapp.service.CommentService;
import com.springapps.redditcloneapp.service.PostService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        Comment comment = null;
        try {
            comment = commentService.addComment(commentRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (JsonProcessingException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
