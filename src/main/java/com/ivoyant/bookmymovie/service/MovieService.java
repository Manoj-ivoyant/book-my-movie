package com.ivoyant.bookmymovie.service;

import com.ivoyant.bookmymovie.dto.MovieDto;
import com.ivoyant.bookmymovie.response.ApiResponse;

import java.util.List;

public interface MovieService {
    ApiResponse addMovie(MovieDto movieDto);

    List<MovieDto> getMovieByName(String movieName);

    List<MovieDto> getAllMovies();
}
