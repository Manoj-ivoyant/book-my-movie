package com.ivoyant.bookmymovie.repository;

import com.ivoyant.bookmymovie.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser_UserId(String userId);

    List<Booking> findAllByTheaterNameAndTheatreLocation(String theatreName,String theatreLocation);

    List<Booking> findAllByUser_UserIdAndPurchasedTimeBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);
}
