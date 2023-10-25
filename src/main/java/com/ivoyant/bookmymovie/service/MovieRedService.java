package com.ivoyant.bookmymovie.service;

import com.ivoyant.bookmymovie.model.MovieRed;

import java.util.List;

public interface MovieRedService {
    List<MovieRed> getMovieByName(String name);
}
