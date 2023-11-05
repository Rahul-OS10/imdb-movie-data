package com.neo4j.imdbmoviedata.controller;

import com.neo4j.imdbmoviedata.entity.Movie;
import com.neo4j.imdbmoviedata.entity.MovieDetailsDto;
import com.neo4j.imdbmoviedata.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/imdb")
public class MovieController {

    @Autowired
    MovieService service;

    @RequestMapping(value = "/getAllMovies" , method = RequestMethod.GET )
    public ResponseEntity<List<Movie>> retrieveAllMovies() {
        List<Movie> allMovies = service.getAllMovies();
        if(!allMovies.isEmpty())
            return  new ResponseEntity<List<Movie>>(allMovies, HttpStatus.OK);
        else
            return new ResponseEntity<>(allMovies,HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/createNewMovie" , method = RequestMethod.POST)
    public ResponseEntity<String> createMovie(@RequestBody Map<String , Object> requestBody)
    {
        Movie movie = new Movie();
        movie.setIds((String) requestBody.get("ids"));
        movie.setTitle((String) requestBody.get("title"));
        movie.setDescription((String) requestBody.get("description"));
        movie.setYear((String) requestBody.get("year"));
        movie.setRuntime((String) requestBody.get("runtime"));
        movie.setRating((String) requestBody.get("rating"));
        movie.setVotes((String) requestBody.get("votes"));
        movie.setRevenue((String) requestBody.get("revenue"));
        service.createMovie(movie);

        // Create relationships with genres, actors, and directors
        if (requestBody.containsKey("genres")) {
            List<String> genres = (List<String>) requestBody.get("genres");
//            for (String genre : genres) {
//                service.createGenreRelationship(genre, movie.title);
//      }
            service.createGenreRelationship(genres , movie.title);
        }

        if (requestBody.containsKey("actors")) {
            List<String> actors = (List<String>) requestBody.get("actors");
//            for (String actor : actors) {
//                service.createActorRelationship(actor , movie.title);
//            }
            service.createActorRelationship(actors , movie.title);
        }

        if (requestBody.containsKey("directors")) {
            List<String> directors = (List<String>) requestBody.get("directors");
//            for (String director : directors) {
//                service.createDirectorRelationship(director , movie.title);
//            }
            service.createDirectorRelationship(directors , movie.title);
        }
        return new ResponseEntity<>("Movie created successfully" , HttpStatus.CREATED);
    }

    @GetMapping("/movieDetails")
    public ResponseEntity<MovieDetailsDto> getMovieDetails(@RequestParam String movieTitle) {
        MovieDetailsDto movieDetails = service.getMovieDetails(movieTitle);

        if (movieDetails != null) {
            return ResponseEntity.ok(movieDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}