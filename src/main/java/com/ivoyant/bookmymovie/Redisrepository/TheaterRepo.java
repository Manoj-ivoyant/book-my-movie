package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.TheaterRed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepo extends CrudRepository<TheaterRed,Long> {
}
