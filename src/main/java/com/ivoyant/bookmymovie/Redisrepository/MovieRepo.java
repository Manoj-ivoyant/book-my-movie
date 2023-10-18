package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends CrudRepository<Movie,Long> {
}
