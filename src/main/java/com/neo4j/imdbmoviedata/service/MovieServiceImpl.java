package com.neo4j.imdbmoviedata.service;

import com.neo4j.imdbmoviedata.entity.Movie;
import com.neo4j.imdbmoviedata.entity.MovieDetailsDto;
import com.neo4j.imdbmoviedata.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    MovieRepository repository;

    @Override
    public List<Movie> getAllMovies() {
        return repository.getAllMoviesInDB();
    }

    @Override
    public Movie createMovie(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public void createGenreRelationship(List<String> genre , String title) {
        repository.createGenreRelationship(genre,title);
    }

    @Override
    public void createActorRelationship(List<String> actor, String title) {
        repository.crateActorRelationship(actor, title);
    }

    @Override
    public void createDirectorRelationship(List<String> director, String title) {
        repository.createDirectorRelationship(director,title);
    }

    public MovieDetailsDto getMovieDetails(String movieTitle) {
        return repository.getMovieDetails(movieTitle);
    }
}