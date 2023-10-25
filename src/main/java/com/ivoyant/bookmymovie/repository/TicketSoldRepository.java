package com.ivoyant.bookmymovie.repository;

import com.ivoyant.bookmymovie.entity.TicketSold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketSoldRepository extends JpaRepository<TicketSold,Long> {
    TicketSold findByTheaterNameAndTheatreLocation(String theaterName,String theatreLocation);
}
