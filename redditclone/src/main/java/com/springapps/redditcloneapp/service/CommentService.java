package com.springapps.redditcloneapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapps.redditcloneapp.dto.CommentRequestDTO;
import com.springapps.redditcloneapp.dto.mapper.CommentMapper;
import com.springapps.redditcloneapp.model.Comment;
import com.springapps.redditcloneapp.model.Post;
import com.springapps.redditcloneapp.model.User;
import com.springapps.redditcloneapp.repository.CommentRepository;
import com.springapps.redditcloneapp.repository.PostRepository;
import com.springapps.redditcloneapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    private UserRepository userRepository;
    private PostRepository postRepository;

    private CommentMapper commentMapper;

    private RestTemplate restTemplate;
    private static final String BASE_URL = "https://api.api-ninjas.com/v1/profanityfilter";
    private ObjectMapper objectMapper;
    private EmailService emailService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, CommentMapper commentMapper, RestTemplate restTemplate, ObjectMapper objectMapper, EmailService emailService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @Transactional
    public Comment addComment(CommentRequestDTO commentRequestDTO) throws JsonProcessingException, MessagingException {
        //verificam textul sa nu contina cuvinte obscene,etc
        if (hasProfanity(commentRequestDTO.getText())) {
            throw new RuntimeException("watch out your words");
        }
        Post post = postRepository.findById(commentRequestDTO.getPostId()).orElseThrow(() -> new RuntimeException("post not found"));
        User user = userRepository.findById(commentRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Comment comment = commentMapper.mapCommentRequestDTOToComment(commentRequestDTO);
        comment.setPost(post);
        comment.setUser(user);
        //timitem mail
        emailService.sendMessage(post.getUser().getEmail(), "new comment at your post", comment.getText() + " BY " + user.getUsername());

        return commentRepository.save(comment);

    }

    public boolean hasProfanity(String text) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("text", text)
                .toUriString();
        //HU8ocdfm9i8g3U9I3nLRiA==tmjgG2VqEchcxCXN
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "HU8ocdfm9i8g3U9I3nLRiA==tmjgG2VqEchcxCXN");
        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
        );
        String response = responseEntity.getBody();
        JsonNode root = objectMapper.readTree(response);
        return root.path("has profanity").asBoolean();

        //URL url = new URL("https://api.api-ninjas.com/v1/profanityfilter?text=damn it!");
        //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestProperty("accept", "application/json");
        //InputStream responseStream = connection.getInputStream();
        //ObjectMapper mapper = new ObjectMapper();
        //JsonNode root = mapper.readTree(responseStream);
        //System.out.println(root.path("fact").asText());
    }

}
