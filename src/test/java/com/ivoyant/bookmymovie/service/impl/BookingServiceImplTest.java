package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.BillingDto;
import com.ivoyant.bookmymovie.entity.*;
import com.ivoyant.bookmymovie.exception.ResourceNotFound;
import com.ivoyant.bookmymovie.repository.*;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.response.BookingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class BookingServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private TheatreRepository theatreRepository;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

//    @Test
//    public void testBookingSuccess() {
//        User user = User.builder().userId("123fdssdf").userName("manoj@gmail.com").password("Manoj@2209").role(Role.USER).build();
//        Wallet wallet = Wallet.builder().walletId(12L).walletType("Paytm").user(user).balance(3000.9).expiryDate(LocalDate.now().plusMonths(2)).build();
//        List<Theatre> theatreList = new ArrayList<>();
//        Theatre theatre = Theatre.builder().theatreId(1l).theatreName("Vasantha").location("Vasantha road").noOfSeats(200).city("Davangere").build();
//        Theatre theatre1 = Theatre.builder().theatreId(2l).theatreName("Ashoka").location("Ashoka road").noOfSeats(200).city("Harihara").build();
//        theatreList.add(theatre);
//        theatreList.add(theatre1);
//        Movie movie = Movie.builder().movieId(3l).movieName("Kgf-2").movieLanguage("Kannada").movieDirector("Prashant neel").price(150.5).theatreList(theatreList).build();
//        BillingDto billingDto = BillingDto.builder().movieName(movie.getMovieName()).theatreName(theatre.getTheatreName()).noOfSeats(2).build();
//        //Booking booking=null;
//        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
//        Mockito.when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(movie);
//        Mockito.when(walletRepository.findById(wallet.getWalletId())).thenReturn(Optional.of(wallet));
//        Mockito.when(theatreRepository.findByTheatreName(billingDto.getTheatreName())).thenReturn(theatre);
//        Mockito.when(walletRepository.findByUserAndWalletId(user, wallet.getWalletId())).thenReturn(wallet);
//        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);
//        Mockito.when(theatreRepository.save(theatre)).thenReturn(theatre);
//        Mockito.when(bookingRepository.save(null)).thenReturn(null);
//        ApiResponse apiResponse = bookingService.bookTicket(wallet.getWalletId(), user.getUserId(), billingDto);
//        Assertions.assertEquals("booking successfully", apiResponse.getMessage());
//        Assertions.assertEquals(HttpStatus.CREATED, apiResponse.getStatus());
//        Mockito.verify(userRepository).findById(user.getUserId());
//        Mockito.verify(movieRepository).findByMovieName(movie.getMovieName());
//
//    }

    @Test
    public void testUserNotFound() {
        BillingDto billingDto = BillingDto.builder().movieName("RRR").theatreName("Pvr").noOfSeats(6).build();
        Assertions.assertThrows(ResourceNotFound.class, () -> bookingService.bookTicket(1l, "123esd2", billingDto));
        Mockito.when(userRepository.findById("123esd2")).thenReturn(Optional.empty());
        Mockito.verify(userRepository).findById("123esd2");
        Mockito.verifyNoInteractions(walletRepository, movieRepository, bookingRepository, theatreRepository);

    }

    @Test
    public void testViewBookedDetailsSuccess() {
        User user = User.builder().userId("123fdssdf").userName("manoj@gmail.com").password("Manoj@2209").role(Role.USER).build();
        List<Theatre> theatreList = new ArrayList<>();
        Theatre theatre = Theatre.builder().theatreId(1l).theatreName("Vasantha").location("Vasantha road").noOfSeats(200).city("Davangere").build();
        Theatre theatre1 = Theatre.builder().theatreId(2l).theatreName("Ashoka").location("Ashoka road").noOfSeats(200).city("Harihara").build();
        theatreList.add(theatre);
        theatreList.add(theatre1);
        Movie movie = Movie.builder().movieId(3l).movieName("Kgf-2").movieLanguage("Kannada").movieDirector("Prashant neel").price(150.5).theatreList(theatreList).build();
        Movie movie2 = Movie.builder().movieId(4l).movieName("RRR").movieLanguage("Telugu").movieDirector("Rajmouli").price(123.3).theatreList(theatreList).build();
        List<Booking> bookings = new ArrayList<>();
        Booking booking = Booking.builder().id(1l).totalPrice(223.3).noOfTickets(2).purchasedTime(LocalDateTime.now()).user(user).movie(movie).build();
        Booking booking2 = Booking.builder().id(2l).totalPrice(123.2).noOfTickets(1).purchasedTime(LocalDateTime.now()).user(user).movie(movie2).build();
        bookings.add(booking);
        bookings.add(booking2);
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(bookingRepository.findAllByUser_UserId(user.getUserId())).thenReturn(bookings);
        List<BookingResponse> bookingResponses = bookingService.viewBookedDetails(user.getUserId());
        Assertions.assertEquals(bookings.size(), bookingResponses.size());
        Mockito.verify(userRepository).findById(user.getUserId());
        Mockito.verify(bookingRepository).findAllByUser_UserId(user.getUserId());

    }

    @Test
    public void testViewBookDetailsEmpty() {
        User user = User.builder().userId("123fdssdf").userName("manoj@gmail.com").password("Manoj@2209").role(Role.USER).build();
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(bookingRepository.findAllByUser_UserId(user.getUserId())).thenReturn(new ArrayList<>(Collections.emptyList()));
        Assertions.assertThrows(ResourceNotFound.class, () -> bookingService.viewBookedDetails(user.getUserId()));
        Mockito.verify(userRepository).findById(user.getUserId());
        Mockito.verifyNoInteractions(walletRepository, theatreRepository, movieRepository);


    }

    @Test
    public void testUserNotFoundForViewDetails() {
        Mockito.when(userRepository.findById("123esd2")).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFound.class, () -> bookingService.viewBookedDetails("123esd2"));
        Mockito.verify(userRepository).findById("123esd2");
        Mockito.verifyNoInteractions(walletRepository, movieRepository, bookingRepository, theatreRepository);

    }

    @Test
    public void testViewBookedByDate() {
        User user = User.builder().userId("123fdssdf").userName("manoj@gmail.com").password("Manoj@2209").role(Role.USER).build();
        List<Theatre> theatreList = new ArrayList<>();
        Theatre theatre = Theatre.builder().theatreId(1l).theatreName("Vasantha").location("Vasantha road").noOfSeats(200).city("Davangere").build();
        Theatre theatre1 = Theatre.builder().theatreId(2l).theatreName("Ashoka").location("Ashoka road").noOfSeats(200).city("Harihara").build();
        theatreList.add(theatre);
        theatreList.add(theatre1);
        Movie movie = Movie.builder().movieId(3l).movieName("Kgf-2").movieLanguage("Kannada").movieDirector("Prashant neel").price(150.5).theatreList(theatreList).build();
        Movie movie2 = Movie.builder().movieId(4l).movieName("RRR").movieLanguage("Telugu").movieDirector("Rajmouli").price(123.3).theatreList(theatreList).build();
        List<Booking> bookings = new ArrayList<>();
        Booking booking = Booking.builder().id(1l).totalPrice(223.3).noOfTickets(2).purchasedTime(LocalDateTime.now()).user(user).movie(movie).build();
        Booking booking2 = Booking.builder().id(2l).totalPrice(123.2).noOfTickets(1).purchasedTime(LocalDateTime.now()).user(user).movie(movie2).build();
        bookings.add(booking);
        bookings.add(booking2);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(bookingRepository.findAllByUser_UserIdAndPurchasedTimeBetween(user.getUserId(), startDate, endDate)).thenReturn(bookings);
        List<BookingResponse> bookingResponses = bookingService.viewBookedDetailsByDate(user.getUserId(), startDate);
        Assertions.assertEquals(bookings.size(), bookingResponses.size());
        Mockito.verify(userRepository).findById(user.getUserId());
        Mockito.verify(bookingRepository).findAllByUser_UserIdAndPurchasedTimeBetween(user.getUserId(), startDate, endDate);
        Mockito.verifyNoInteractions(walletRepository, theatreRepository, movieRepository);
    }

    @Test
    public void testViewBookByDateEmpty() {
        User user = User.builder().userId("123fdssdf").userName("manoj@gmail.com").password("Manoj@2209").role(Role.USER).build();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(bookingRepository.findAllByUser_UserIdAndPurchasedTimeBetween(user.getUserId(),startDate,endDate)).thenReturn(new ArrayList<>(Collections.emptyList()));
        Assertions.assertThrows(ResourceNotFound.class, () -> bookingService.viewBookedDetailsByDate(user.getUserId(),startDate));
        Mockito.verify(userRepository).findById(user.getUserId());
        Mockito.verifyNoInteractions(walletRepository, theatreRepository, movieRepository);

    }
    @Test
    public void testUserNotFoundForViewDetailsByDate() {
        Mockito.when(userRepository.findById("123esd2")).thenReturn(Optional.empty());
        LocalDateTime startDate = LocalDateTime.now();
        Assertions.assertThrows(ResourceNotFound.class, () -> bookingService.viewBookedDetailsByDate("123esd2",startDate));
        Mockito.verify(userRepository).findById("123esd2");
        Mockito.verifyNoInteractions(walletRepository, movieRepository, bookingRepository, theatreRepository);

    }

}
