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
public class MovieDetailsDto {
    private Movie movie;
    private List<String> actors;
    private List<String> directors;
    private List<String> genre;

    public MovieDetailsDto(Movie movie, List<String> actors, List<String> directors, List<String> genre) {
        this.movie = movie;
        this.actors = actors;
        this.directors = directors;
        this.genre = genre;
    }
}
