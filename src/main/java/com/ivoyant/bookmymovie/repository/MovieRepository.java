package com.ivoyant.bookmymovie.repository;

import com.ivoyant.bookmymovie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByMovieNameAndMovieLanguage(String movieName,String movieLanguage);

    List<Movie> findAllByMovieName(String movieName);
}
