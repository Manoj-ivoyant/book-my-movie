package com.ivoyant.bookmymovie.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private Integer noOfTickets;
    private Double totalPrice;
    private LocalDateTime purchasedTime;
    private String userName;
    private String movieName;
    private String theaterName;
}
