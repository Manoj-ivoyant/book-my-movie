package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.config.Constants;
import com.ivoyant.bookmymovie.dto.MovieDto;
import com.ivoyant.bookmymovie.dto.TheatreDto;
import com.ivoyant.bookmymovie.entity.Movie;
import com.ivoyant.bookmymovie.entity.Theatre;
import com.ivoyant.bookmymovie.exception.ResourceConflictExists;
import com.ivoyant.bookmymovie.exception.ResourceNotFound;
import com.ivoyant.bookmymovie.model.MovieRed;
import com.ivoyant.bookmymovie.model.TheaterRed;
import com.ivoyant.bookmymovie.repository.MovieRepository;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ApiResponse addMovie(MovieDto movieDto) {
        //persisting data to redis
        List<MovieRed> movieReds = redisTemplate.opsForHash().values(Constants.MOVIE_KEY);
        MovieRed movieRed = movieReds.stream().filter(i -> movieDto.getMovieName().equals(i.getMovieName()) && movieDto.
                getMovieLanguage().equals(i.getMovieLanguage())).findFirst().orElse(null);
        if (movieRed == null) {
            Long id = new Random().nextLong();
            MovieRed movieRed1 = MovieRed.builder().movieName(movieDto.getMovieName())
                    .movieDirector(movieDto.getMovieDirector()).movieLanguage(movieDto.getMovieLanguage())
                    .price(movieDto.getPrice()).releasedAt(LocalDateTime.now()).movieId(id).build();
            Map<Long, MovieRed> movieRedMap = new HashMap<>();
            movieRedMap.put(id, movieRed1);
            redisTemplate.opsForHash().putAll(Constants.MOVIE_KEY, movieRedMap);
            List<TheaterRed> theaterReds = movieDto.getTheatreDtoList().stream().map(i -> TheaterRed.builder()
                    .theatreId(new Random().nextLong()).movieId(id).theatreName(i.getTheatreName()).
                    noOfSeats(i.getNoOfSeats())
                    .location(i.getLocation()).releasedOn(LocalDateTime.now()).city(i.getCity()).build()).toList();
            Map<Long, TheaterRed> theaterRedMap = theaterReds.stream().collect(Collectors.toMap(TheaterRed::getTheatreId,
                    Function.identity()));
            redisTemplate.opsForHash().putAll(Constants.THEATER_KEY, theaterRedMap);
        } else {
            throw new ResourceConflictExists("movie already reported");
        }

        //persisting data to mysql
        List<Theatre> theatreList = movieDto.getTheatreDtoList().stream().map(i -> {
            return Theatre.builder().theatreName(i.getTheatreName()).city(i.getCity()).location(i.getLocation())
                    .noOfSeats(i.getNoOfSeats()).build();
        }).toList();
        Movie movie2 = movieRepository.findByMovieNameAndMovieLanguage(movieDto.getMovieName(), movieDto
                .getMovieLanguage());
        if (movie2 != null) {
            throw new ResourceConflictExists("movie already exist with same language");
        }
        Movie movie = Movie.builder().movieName(movieDto.getMovieName()).movieLanguage(movieDto.getMovieLanguage())
                .movieDirector(movieDto.getMovieDirector()).price(movieDto.getPrice()).theatreList(theatreList).build();
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
                return TheatreDto.builder().theatreName(j.getTheatreName()).noOfSeats(j.getNoOfSeats())
                        .city(j.getCity()).location(j.getLocation()).build();
            }).toList();
            return MovieDto.builder().movieName(i.getMovieName()).movieLanguage(i.getMovieLanguage())
                    .movieDirector(i.getMovieDirector()).price(i.getPrice()).theatreDtoList(theatreDtoList).build();

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
                return TheatreDto.builder().theatreName(j.getTheatreName()).noOfSeats(j.getNoOfSeats())
                        .city(j.getCity()).location(j.getLocation()).build();
            }).toList();
            return MovieDto.builder().movieName(i.getMovieName()).movieLanguage(i.getMovieLanguage())
                    .movieDirector(i.getMovieDirector()).price(i.getPrice()).theatreDtoList(theatreDtoList).build();

        }).toList();

    }
}
