package com.ivoyant.bookmymovie.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "Booking",timeToLive = 86400L)
public class BookingRed implements Serializable {
    @Id
    private Long id;
    private Integer noOfTickets;
    private Double totalPrice;
    @CreatedDate
    private LocalDateTime purchasedTime;
    private String userId;
    private Long movieId;
    private String theatreName;
    private String theatreLocation;
}
