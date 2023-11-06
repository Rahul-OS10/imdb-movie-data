package com.neo4j.imdbmoviedata.repository;

import com.neo4j.imdbmoviedata.entity.Movie;
import com.neo4j.imdbmoviedata.entity.MovieDetailsDto;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MovieRepository extends Neo4jRepository<Movie, String> {

    //@Query("MATCH (m:Movie) RETURN  m.id AS id, m.title AS title, m.description AS description, m.revenue AS revenue,m.year AS year,m.ids AS ids, m.runtime AS runtime, m.rating AS rating, m.votes AS votes, m.genres AS genres")
    @Query("MATCH (m:Movie) RETURN  m.title AS title, m.description AS description, m.revenue AS revenue,m.year AS year,m.ids AS ids, m.runtime AS runtime, m.rating AS rating, m.votes AS votes")
    List<Movie> getAllMoviesInDB();

    @Query("MATCH (movie:Movie {title: $movieTitle})\n" +
            "UNWIND $genres AS genre\n" +
            "MERGE (g:Genres {type: genre})\n" +
            "CREATE (movie)-[:IN]->(g)")                                          // this is the excat query that is not creating duplicate relaionships !!!
    void createGenreRelationship(List<String> genres, String movieTitle);

    @Query("MATCH (movie:Movie {title: $movieTitle})\n" +
            "WITH movie\n" +
            "UNWIND $actors AS actor\n" +
            "MERGE (a:Person {name: actor})\n" +
            "CREATE (a)-[:ACTED_IN]->(movie)")
    void crateActorRelationship(List<String> actors, String movieTitle);


    @Query("MATCH (movie:Movie {title: $movieTitle})\n" +
            "WITH movie\n" +
            "UNWIND $directors AS director\n" +
            "MERGE (d:Person {name: director})\n" +
            "CREATE (d)-[:DIRECTED]->(movie)")
    void createDirectorRelationship(List<String> directors, String movieTitle);


    @Query("MATCH (m:Movie{title:$movieTitle})-[:IN]->(g:Genres)\n" +
            "                        OPTIONAL MATCH (m)<-[:DIRECTED]-(d:Person)\n" +
            "                        OPTIONAL MATCH (m)<-[:ACTED_IN]-(a:Person)\n" +
            "                        WITH m AS movie, COLLECT(DISTINCT a.name) AS actors, COLLECT(DISTINCT g.type) AS genre, COLLECT(DISTINCT d.name) AS directors\n" +
            "                        RETURN movie,actors,directors,genre")
    MovieDetailsDto getMovieDetails(@RequestParam("movieTitle") String movieTitle);

    @Query("MATCH (movie:Movie {title: $movieTitle}) DETACH DELETE movie")
    void deleteMovieByTitle(String movieTitle);

    @Query("MATCH (movie:Movie {title: $movieTitle}) SET movie.title = $newTitle, movie.description = $newDescription, movie.rating = $newRating RETURN movie")
    Movie updateMovieByTitle( String movieTitle, String newTitle, String newDescription, String newRating);


}