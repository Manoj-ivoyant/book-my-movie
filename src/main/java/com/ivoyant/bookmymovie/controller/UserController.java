package com.ivoyant.bookmymovie.controller;

import com.ivoyant.bookmymovie.dto.UserDto;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.response.BookingResponse;
import com.ivoyant.bookmymovie.service.BookingService;
import com.ivoyant.bookmymovie.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.registerUser(userDto));

    }

    @GetMapping("/{userName}/bookings")
    public ResponseEntity<List<BookingResponse>> viewBookedDetails(@PathVariable String userName) {
        return ResponseEntity.ok().body(bookingService.viewBookedDetails(userName));

    }

    @GetMapping("/{userName}/booking")
    public ResponseEntity<List<BookingResponse>> viewBookDetailsByDate(@PathVariable String userName, @RequestParam("month") int month, @RequestParam("year") int year) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        return ResponseEntity.ok().body(bookingService.viewBookedDetailsByDate(userName, startDate));
    }
}
