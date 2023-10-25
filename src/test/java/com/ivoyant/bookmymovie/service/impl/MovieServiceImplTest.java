package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.MovieDto;
import com.ivoyant.bookmymovie.dto.TheatreDto;
import com.ivoyant.bookmymovie.entity.Movie;
import com.ivoyant.bookmymovie.entity.Theatre;
import com.ivoyant.bookmymovie.exception.ResourceNotFound;
import com.ivoyant.bookmymovie.repository.MovieRepository;
import com.ivoyant.bookmymovie.response.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class MovieServiceImplTest {
    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @Test
    public void testAddMovie() {
        List<TheatreDto> theatreDtoList = new ArrayList<>();
        TheatreDto theatreDto = TheatreDto.builder().theatreName("Vasantha").location("Vasantha road").noOfSeats(200).city("Davangere").build();
        TheatreDto theatreDto1 = TheatreDto.builder().theatreName("Ashoka").location("Ashoka road").noOfSeats(200).city("Harihara").build();
        theatreDtoList.add(theatreDto);
        theatreDtoList.add(theatreDto1);
        MovieDto movieDto = MovieDto.builder().movieName("Kgf-2").movieLanguage("Kannada").movieDirector("Prashant neel").price(150.5).theatreDtoList(theatreDtoList).build();
        Movie movie = new Movie();
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        ApiResponse apiResponse = movieService.addMovie(movieDto);
        Assertions.assertEquals("movie added successfully", apiResponse.getMessage());
        Assertions.assertEquals(HttpStatus.CREATED, apiResponse.getStatus());
        // Mockito.verify(movieRepository).save(movie);

    }

//    @Test
//    public void testGetMovieByNameSuccess() {
//        List<Theatre> theatreList = new ArrayList<>();
//        Theatre theatre = Theatre.builder().theatreId(1l).theatreName("Vasantha").location("Vasantha road").noOfSeats(200).city("Davangere").build();
//        Theatre theatre1 = Theatre.builder().theatreId(2l).theatreName("Ashoka").location("Ashoka road").noOfSeats(200).city("Harihara").build();
//        Movie movie = Movie.builder().movieId(1234l).movieName("kgf-2").movieLanguage("Kannada").movieDirector("Prashant neel").price(150.53).theatreList(theatreList).build();
//        Mockito.when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(movie);
//       // MovieDto movieDto = movieService.getMovieByName(movie.getMovieName());
//        Assertions.assertEquals(movie.getMovieName(), movieDto.getMovieName());
//        Assertions.assertEquals(movie.getMovieLanguage(), movieDto.getMovieLanguage());
//        Assertions.assertEquals(150.53, movieDto.getPrice());
//        Mockito.verify(movieRepository).findByMovieName(movie.getMovieName());
//
//
//    }

//    @Test
//    public void testGetMovieByNameFailure() {
//        String movieName = "RRR";
//        // Movie movie=null;
//        Mockito.when(movieRepository.findByMovieName(movieName)).thenReturn(null);
//        Assertions.assertThrows(ResourceNotFound.class, () -> movieService.getMovieByName(movieName));
//        Mockito.verify(movieRepository).findByMovieName(movieName);
//    }

    @Test
    public void testGetAllMoviesSuccess() {
        List<Theatre> theatreList = new ArrayList<>();
        Theatre theatre = Theatre.builder().theatreId(1l).theatreName("Vasantha").location("Vasantha road").noOfSeats(200).city("Davangere").build();
        Theatre theatre1 = Theatre.builder().theatreId(2l).theatreName("Ashoka").location("Ashoka road").noOfSeats(200).city("Harihara").build();
        theatreList.add(theatre);
        theatreList.add(theatre1);
        Movie movie = Movie.builder().movieId(3l).movieName("Kgf-2").movieLanguage("Kannada").movieDirector("Prashant neel").price(150.5).theatreList(theatreList).build();
        Movie movie2 = Movie.builder().movieId(4l).movieName("RRR").movieLanguage("Telugu").movieDirector("Rajmouli").price(123.3).theatreList(theatreList).build();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie2);
        Mockito.when(movieRepository.findAll()).thenReturn(movies);
        List<MovieDto> movieDtos = movieService.getAllMovies();
        Assertions.assertEquals(movies.size(), movieDtos.size());
        Mockito.verify(movieRepository).findAll();
    }

    @Test
    public void testGetAllMovieEmpty() {
        Mockito.when(movieRepository.findAll()).thenReturn(new ArrayList<>(Collections.emptyList()));
        Assertions.assertThrows(ResourceNotFound.class, () -> movieService.getAllMovies());
        Mockito.verify(movieRepository).findAll();


    }
}
