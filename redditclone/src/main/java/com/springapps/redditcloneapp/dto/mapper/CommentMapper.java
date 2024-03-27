package com.springapps.redditcloneapp.dto.mapper;

import com.springapps.redditcloneapp.dto.CommentRequestDTO;
import com.springapps.redditcloneapp.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mapping(target = "id", ignore=true)
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Comment mapCommentRequestDTOToComment(CommentRequestDTO commentRequestDTO);
}
