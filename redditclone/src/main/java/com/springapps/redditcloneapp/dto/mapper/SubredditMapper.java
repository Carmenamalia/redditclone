package com.springapps.redditcloneapp.dto.mapper;

import com.springapps.redditcloneapp.dto.SubredditRequestDTO;
import com.springapps.redditcloneapp.model.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class SubredditMapper {

    @Mapping(target = "id", ignore=true)
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Subreddit mapSubredditRequestDTOtoSubreddit(SubredditRequestDTO subredditRequestDTO);
}
