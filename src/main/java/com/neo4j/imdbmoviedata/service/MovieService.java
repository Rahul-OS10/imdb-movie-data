package com.neo4j.imdbmoviedata.service;

import com.neo4j.imdbmoviedata.entity.Movie;
import com.neo4j.imdbmoviedata.entity.MovieDetailsDto;

import java.util.List;

public interface MovieService {

    public List<Movie> getAllMovies();

    public Movie createMovie(Movie movie);


    void createGenreRelationship(List<String> genre, String title);


    void createActorRelationship(List<String> actor, String title);

    void createDirectorRelationship(List<String> director, String title);

    public MovieDetailsDto getMovieDetails(String movieTitle) ;


}