package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.Theater;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepo extends CrudRepository<Theater,Long> {
}
