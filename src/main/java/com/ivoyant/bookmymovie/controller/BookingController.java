package com.ivoyant.bookmymovie.controller;

import com.ivoyant.bookmymovie.dto.BillingDto;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse> bookTicket(@RequestHeader Long walletId, @RequestHeader String userName,@Valid @RequestBody BillingDto billingDto){
        return ResponseEntity.ok().body(bookingService.bookTicket(walletId,userName,billingDto));
    }

}
