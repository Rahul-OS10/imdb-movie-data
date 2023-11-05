package com.neo4j.imdbmoviedata.entity;

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
    private List<String> genres;
}
