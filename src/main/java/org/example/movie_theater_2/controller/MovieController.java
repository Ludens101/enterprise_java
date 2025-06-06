package org.example.movie_theater_2.controller;


import org.example.movie_theater_2.model.Movie;
import org.example.movie_theater_2.repository.MovieRepository;
import org.example.movie_theater_2.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class.getName());

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Movie>> getAllMovies() {
        logger.info("getAllMovies called");
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/view")
    public String viewMovies(Model model) {
        logger.info("viewMovies called");
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(savedMovie);
    }
}
