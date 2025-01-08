package com.neo4j.imdbmoviedata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo4j.imdbmoviedata.entity.Movie;
import com.neo4j.imdbmoviedata.entity.MovieDetailsDto;
import com.neo4j.imdbmoviedata.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private Movie movie;
    private MovieDetailsDto movieDetailsDto;

    @BeforeEach
    public void setUp() {
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
    public void testRetrieveAllMovies() throws Exception {
        List<Movie> movies = Arrays.asList(movie);
        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/imdb"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Movie"));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    public void testRetrieveAllMovies_NoContent() throws Exception {
        when(movieService.getAllMovies()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/imdb"))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    public void testCreateMovie() throws Exception {
        String json = objectMapper.writeValueAsString(movie);

        mockMvc.perform(post("/imdb")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(movieService, times(1)).createMovie(any(Movie.class));
    }

    @Test
    public void testGetMovieDetails() throws Exception {
        when(movieService.getMovieDetails(anyString())).thenReturn(movieDetailsDto);

        mockMvc.perform(get("/imdb/Test Movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movie.title").value("Test Movie"));

        verify(movieService, times(1)).getMovieDetails(anyString());
    }

    @Test
    public void testGetMovieDetails_NotFound() throws Exception {
        when(movieService.getMovieDetails(anyString())).thenReturn(null);

        mockMvc.perform(get("/imdb/Test Movie"))
                .andExpect(status().isNotFound());

        verify(movieService, times(1)).getMovieDetails(anyString());
    }

    @Test
    public void testDeleteMovie() throws Exception {
        when(movieService.getMovieDetails(anyString())).thenReturn(movie);

        mockMvc.perform(delete("/imdb/Test Movie"))
                .andExpect(status().isOk());

        verify(movieService, times(1)).deleteMovieByTitle(anyString());
    }

    @Test
    public void testDeleteMovie_NoContent() throws Exception {
        when(movieService.getMovieDetails(anyString())).thenReturn(null);

        mockMvc.perform(delete("/imdb/Test Movie"))
                .andExpect(status().isNoContent());

        verify(movieService, times(0)).deleteMovieByTitle(anyString());
    }

    @Test
    public void testUpdateMovie() throws Exception {
        when(movieService.getMovieDetails(anyString())).thenReturn(movie);
        when(movieService.updateMovieByTitle(anyString(), anyString(), anyString(), anyString())).thenReturn(movie);

        mockMvc.perform(patch("/imdb")
                        .param("title", "Test Movie")
                        .param("newTitle", "Updated Movie")
                        .param("newDescription", "Updated Description")
                        .param("newRating", "9.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Movie"));

        verify(movieService, times(1)).updateMovieByTitle(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testUpdateMovie_NotFound() throws Exception {
        when(movieService.getMovieDetails(anyString())).thenReturn(null);

        mockMvc.perform(patch("/imdb")
                        .param("title", "Test Movie")
                        .param("newTitle", "Updated Movie")
                        .param("newDescription", "Updated Description")
                        .param("newRating", "9.0"))
                .andExpect(status().isNotFound());

        verify(movieService, times(0)).updateMovieByTitle(anyString(), anyString(), anyString(), anyString());
    }
}
