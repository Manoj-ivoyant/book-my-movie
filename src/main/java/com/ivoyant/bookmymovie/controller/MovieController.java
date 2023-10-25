package com.ivoyant.bookmymovie.controller;

import com.ivoyant.bookmymovie.dto.MovieDto;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping
    public ResponseEntity<ApiResponse> addMovie(@Valid @RequestBody MovieDto movieDto) {
        return ResponseEntity.ok().body(movieService.addMovie(movieDto));
    }

    @GetMapping("/{movieName}")
    public ResponseEntity<List<MovieDto>> getMovieByMovieName(@PathVariable String movieName) {
        return ResponseEntity.ok().body(movieService.getMovieByName(movieName));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok().body(movieService.getAllMovies());

    }
}
