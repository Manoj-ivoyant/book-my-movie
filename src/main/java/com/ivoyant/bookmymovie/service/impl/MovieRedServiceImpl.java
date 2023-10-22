package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.config.Constants;
import com.ivoyant.bookmymovie.model.MovieRed;
import com.ivoyant.bookmymovie.service.MovieRedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieRedServiceImpl implements MovieRedService {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger logger= LoggerFactory.getLogger(MovieRedServiceImpl.class);

    @Override
    public List<MovieRed> getMovieByName(String name) {
        List<MovieRed> movieReds = redisTemplate.opsForHash().values(Constants.MOVIE_KEY);
        logger.info("from database");
        return movieReds.stream().filter(i -> i.getMovieName().equals(name)).toList();
    }
}
