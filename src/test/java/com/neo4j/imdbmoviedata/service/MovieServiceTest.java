package com.neo4j.imdbmoviedata.service;

import com.neo4j.imdbmoviedata.entity.Movie;
import com.neo4j.imdbmoviedata.entity.MovieDetailsDto;
import com.neo4j.imdbmoviedata.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private MovieDetailsDto movieDetailsDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movie = new Movie();
        movie.setIds("1");
        movie.setTitle("Test Movie");
        movie.setDescription("Test Description");
        movie.setYear("2022");
        movie.setRuntime("120");
        movie.setRating("8.5");
        movie.setVotes("1000");
        movie.setRevenue("1000000");

        movieDetailsDto = new MovieDetailsDto(movie, Arrays.asList("Actor1", "Actor2"), Arrays.asList("Director1"), Arrays.asList("Genre1", "Genre2"));
    }

    @Test
    public void testGetAllMovies() {
        List<Movie> movies = Arrays.asList(movie);
        when(movieRepository.getAllMoviesInDB()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();
        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());

        verify(movieRepository, times(1)).getAllMoviesInDB();
    }

    @Test
    public void testCreateMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.createMovie(movie);
        assertEquals("Test Movie", result.getTitle());

        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    public void testCreateGenreRelationship() {
        doNothing().when(movieRepository).createGenreRelationship(anyList(), anyString());

        movieService.createGenreRelationship(Arrays.asList("Genre1", "Genre2"), "Test Movie");

        verify(movieRepository, times(1)).createGenreRelationship(anyList(), anyString());
    }

    @Test
    public void testCreateActorRelationship() {
        doNothing().when(movieRepository).crateActorRelationship(anyList(), anyString());

        movieService.createActorRelationship(Arrays.asList("Actor1", "Actor2"), "Test Movie");

        verify(movieRepository, times(1)).crateActorRelationship(anyList(), anyString());
    }

    @Test
    public void testCreateDirectorRelationship() {
        doNothing().when(movieRepository).createDirectorRelationship(anyList(), anyString());

        movieService.createDirectorRelationship(Arrays.asList("Director1"), "Test Movie");

        verify(movieRepository, times(1)).createDirectorRelationship(anyList(), anyString());
    }

    @Test
    public void testGetMovieDetails() {
        when(movieRepository.getMovieDetails(anyString())).thenReturn(movieDetailsDto);

        MovieDetailsDto result = movieService.getMovieDetails("Test Movie");
        assertEquals("Test Movie", result.getMovie().getTitle());

        verify(movieRepository, times(1)).getMovieDetails(anyString());
    }

    @Test
    public void testDeleteMovieByTitle() {
        doNothing().when(movieRepository).deleteMovieByTitle(anyString());

        movieService.deleteMovieByTitle("Test Movie");

        verify(movieRepository, times(1)).deleteMovieByTitle(anyString());
    }

    @Test
    public void testUpdateMovieByTitle() {
        when(movieRepository.updateMovieByTitle(anyString(), anyString(), anyString(), anyString())).thenReturn(movie);

        Movie result = movieService.updateMovieByTitle("Test Movie", "Updated Movie", "Updated Description", "9.0");
        assertEquals("Test Movie", result.getTitle());

        verify(movieRepository, times(1)).updateMovieByTitle(anyString(), anyString(), anyString(), anyString());
    }
}
