package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.BookingRed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends CrudRepository<BookingRed,Long> {
}
