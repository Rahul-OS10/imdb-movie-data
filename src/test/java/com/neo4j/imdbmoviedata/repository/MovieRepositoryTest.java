package com.neo4j.imdbmoviedata.repository;

import com.neo4j.imdbmoviedata.entity.Movie;
import com.neo4j.imdbmoviedata.entity.MovieDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataNeo4jTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private Neo4jClient neo4jClient;

    @Test
    void testGetAllMoviesInDB() {
        List<Movie> movies = movieRepository.getAllMoviesInDB();
        assertThat(movies).isNotNull();
    }

    @Test
    void testCreateGenreRelationship() {
        String movieTitle = "Test Movie";
        List<String> genres = List.of("Action", "Drama");
        movieRepository.createGenreRelationship(genres, movieTitle);

        List<String> result = neo4jClient.query("MATCH (m:Movie {title: $movieTitle})-[:IN]->(g:Genres) RETURN g.type")
                .bind(movieTitle).to("movieTitle")
                .fetchAs(String.class)
                .mappedBy((typeSystem, record) -> record.get("g.type").asString())
                .all();

        assertThat(result).containsExactlyInAnyOrderElementsOf(genres);
    }

    @Test
    void testCreateActorRelationship() {
        String movieTitle = "Test Movie";
        List<String> actors = List.of("Actor1", "Actor2");
        movieRepository.crateActorRelationship(actors, movieTitle);

        List<String> result = neo4jClient.query("MATCH (m:Movie {title: $movieTitle})<-[:ACTED_IN]-(a:Person) RETURN a.name")
                .bind(movieTitle).to("movieTitle")
                .fetchAs(String.class)
                .mappedBy((typeSystem, record) -> record.get("a.name").asString())
                .all();

        assertThat(result).containsExactlyInAnyOrderElementsOf(actors);
    }

    @Test
    void testCreateDirectorRelationship() {
        String movieTitle = "Test Movie";
        List<String> directors = List.of("Director1", "Director2");
        movieRepository.createDirectorRelationship(directors, movieTitle);

        List<String> result = neo4jClient.query("MATCH (m:Movie {title: $movieTitle})<-[:DIRECTED]-(d:Person) RETURN d.name")
                .bind(movieTitle).to("movieTitle")
                .fetchAs(String.class)
                .mappedBy((typeSystem, record) -> record.get("d.name").asString())
                .all();

        assertThat(result).containsExactlyInAnyOrderElementsOf(directors);
    }

    @Test
    void testGetMovieDetails() {
        String movieTitle = "Test Movie";
        MovieDetailsDto movieDetails = movieRepository.getMovieDetails(movieTitle);
        assertThat(movieDetails).isNotNull();
    }

    @Test
    void testDeleteMovieByTitle() {
        String movieTitle = "Test Movie";
        movieRepository.deleteMovieByTitle(movieTitle);

        List<Movie> result = neo4jClient.query("MATCH (m:Movie {title: $movieTitle}) RETURN m")
                .bind(movieTitle).to("movieTitle")
                .fetchAs(Movie.class)
                .all();

        assertThat(result).isEmpty();
    }

    @Test
    void testUpdateMovieByTitle() {
        String movieTitle = "Test Movie";
        String newTitle = "Updated Movie";
        String newDescription = "Updated Description";
        String newRating = "9.0";
        Movie updatedMovie = movieRepository.updateMovieByTitle(movieTitle, newTitle, newDescription, newRating);

        assertThat(updatedMovie).isNotNull();
        assertThat(updatedMovie.getTitle()).isEqualTo(newTitle);
        assertThat(updatedMovie.getDescription()).isEqualTo(newDescription);
        assertThat(updatedMovie.getRating()).isEqualTo(newRating);
    }
}
