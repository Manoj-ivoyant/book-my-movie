package com.ivoyant.bookmymovie.controller;

import com.ivoyant.bookmymovie.model.MovieRed;
import com.ivoyant.bookmymovie.service.MovieRedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movieredis")
@EnableCaching
public class MovieRedController {
    @Autowired
    private MovieRedService movieRedService;

    @GetMapping("/{name}")
    @Cacheable(key = "#name", value = "MovieRed")
    public List<MovieRed> getMovieByname(@PathVariable String name) {
        return movieRedService.getMovieByName(name);
    }
}
