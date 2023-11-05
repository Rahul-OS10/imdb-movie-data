package com.neo4j.imdbmoviedata.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsDto {
    private Movie movie;
    private List<String> actors;
    private List<String> directors;
    @JsonIgnore
    private List<String> genres;
}
