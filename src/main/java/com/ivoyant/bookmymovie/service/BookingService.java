package com.ivoyant.bookmymovie.service;

import com.ivoyant.bookmymovie.dto.BillingDto;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.response.BookingResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    ApiResponse bookTicket(Long walletId, String userName, BillingDto billingDto);

    List<BookingResponse> viewBookedDetails(String userName);

    List<BookingResponse> viewBookedDetailsByDate(String userName, LocalDateTime startDate);
}
