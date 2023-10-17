package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.MovieDto;
import com.ivoyant.bookmymovie.dto.TheatreDto;
import com.ivoyant.bookmymovie.entity.Movie;
import com.ivoyant.bookmymovie.entity.Theatre;
import com.ivoyant.bookmymovie.exception.ResourceConflictExists;
import com.ivoyant.bookmymovie.exception.ResourceNotFound;
import com.ivoyant.bookmymovie.repository.MovieRepository;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public ApiResponse addMovie(MovieDto movieDto) {
        List<Theatre> theatreList = movieDto.getTheatreDtoList().stream().map(i -> {
            return Theatre.builder().theatreName(i.getTheatreName()).city(i.getCity()).location(i.getLocation()).noOfSeats(i.getNoOfSeats()).build();
        }).toList();
        Movie movie2=movieRepository.findByMovieNameAndMovieLanguage(movieDto.getMovieName(),movieDto.getMovieLanguage());
        if(movie2!=null){
            throw new ResourceConflictExists("movie already exist with same language");
        }
        Movie movie = Movie.builder().movieName(movieDto.getMovieName()).movieLanguage(movieDto.getMovieLanguage()).movieDirector(movieDto.getMovieDirector()).price(movieDto.getPrice()).theatreList(theatreList).build();
        movieRepository.save(movie);
        return ApiResponse.builder().message("movie added successfully").status(HttpStatus.CREATED).build();
    }

    @Override
    public List<MovieDto> getMovieByName(String movieName) {
        List<Movie> movies = movieRepository.findAllByMovieName(movieName);
        if (movies.isEmpty()) {
            throw new ResourceNotFound("movie not found");

        }
        return movies.stream().map(i -> {
            List<Theatre> theatreList = i.getTheatreList();
            List<TheatreDto> theatreDtoList = theatreList.stream().map(j -> {
                return TheatreDto.builder().theatreName(j.getTheatreName()).noOfSeats(j.getNoOfSeats()).city(j.getCity()).location(j.getLocation()).build();
            }).toList();
            return MovieDto.builder().movieName(i.getMovieName()).movieLanguage(i.getMovieLanguage()).movieDirector(i.getMovieDirector()).price(i.getPrice()).theatreDtoList(theatreDtoList).build();

        }).toList();
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            throw new ResourceNotFound("movieList is empty");
        }
        return movies.stream().map(i -> {
            List<Theatre> theatreList = i.getTheatreList();
            List<TheatreDto> theatreDtoList = theatreList.stream().map(j -> {
                return TheatreDto.builder().theatreName(j.getTheatreName()).noOfSeats(j.getNoOfSeats()).city(j.getCity()).location(j.getLocation()).build();
            }).toList();
            return MovieDto.builder().movieName(i.getMovieName()).movieLanguage(i.getMovieLanguage()).movieDirector(i.getMovieDirector()).price(i.getPrice()).theatreDtoList(theatreDtoList).build();

        }).toList();

    }
}
