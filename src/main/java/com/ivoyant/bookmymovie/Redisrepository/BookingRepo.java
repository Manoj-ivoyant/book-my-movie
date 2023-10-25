package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends CrudRepository<Booking,Long> {
}
