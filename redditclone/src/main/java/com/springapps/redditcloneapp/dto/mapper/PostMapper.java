package com.springapps.redditcloneapp.dto.mapper;

import com.springapps.redditcloneapp.dto.PostRequestDTO;
import com.springapps.redditcloneapp.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "voteCount",constant = "0L")
    @Mapping(target = "creationDate",expression = "java(java.time.LocalDateTime.now())")
    public abstract Post mapPostRequestDTOtoPost(PostRequestDTO postRequestDTO);
}
