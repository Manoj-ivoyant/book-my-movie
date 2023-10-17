package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.BillingDto;
import com.ivoyant.bookmymovie.entity.*;
import com.ivoyant.bookmymovie.exception.ResourceConflictExists;
import com.ivoyant.bookmymovie.exception.ResourceNotFound;
import com.ivoyant.bookmymovie.repository.*;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.response.BookingResponse;
import com.ivoyant.bookmymovie.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TicketSoldRepository ticketSoldRepository;

    @Override
    @Transactional
    public ApiResponse bookTicket(Long walletId, String userName, BillingDto billingDto) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new ResourceNotFound("user not found");
        }
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFound("wallet with id:" + walletId + " not found"));
        List<Wallet> walletList = user.getWallets();
        boolean cond = walletList.contains(wallet);
        Movie movie = movieRepository.findByMovieNameAndMovieLanguage(billingDto.getMovieName(), billingDto.getMovieLanguage());
        List<Theatre> theatreList = movie.getTheatreList();
        Theatre theatre = theatreRepository.findByTheatreNameAndLocation(billingDto.getTheatreName(), billingDto.getLocation());
        boolean cond2 = theatreList.contains(theatre);
        if (cond2) {
            if (movie == null || theatre == null) {
                throw new ResourceNotFound("movie or theatre not found");
            }

            if (!cond) {
                throw new ResourceNotFound("wallet:" + walletId + " not for the user:" + userName);
            }
            Double price = movie.getPrice();
            Integer seats = theatre.getNoOfSeats();
            if (seats >= billingDto.getNoOfSeats()) {
                Double totalPrice = billingDto.getNoOfSeats() * price;
                if (totalPrice <= wallet.getBalance()) {
                    Booking booking = Booking.builder().movie(movie).noOfTickets(billingDto.getNoOfSeats()).totalPrice(totalPrice).user(user).theaterName(billingDto.getTheatreName()).theatreLocation(theatre.getLocation()).build();
                    theatre.setNoOfSeats(seats - booking.getNoOfTickets());
                    wallet.setBalance(wallet.getBalance() - totalPrice);
                    TicketSold ticketSold = ticketSoldRepository.findByTheaterNameAndTheatreLocation(billingDto.getTheatreName(), billingDto.getLocation());
                    if (ticketSold != null) {
                        ticketSold.setTicketsSold(ticketSold.getTicketsSold() + billingDto.getNoOfSeats());
                    } else {
                        ticketSold = new TicketSold();
                        ticketSold.setTheaterName(billingDto.getTheatreName());
                        ticketSold.setTicketsSold(billingDto.getNoOfSeats());
                        ticketSold.setTheatreLocation(billingDto.getLocation());
                    }
                    ticketSoldRepository.save(ticketSold);
                    walletRepository.save(wallet);
                    theatreRepository.save(theatre);
                    bookingRepository.save(booking);
                } else {
                    throw new ResourceConflictExists("Insufficient balance");
                }

            } else {
                throw new ResourceConflictExists("house full");
            }
        } else {
            throw new ResourceConflictExists("movie not host in theatre selected");
        }
        return new ApiResponse("booking successfully", HttpStatus.CREATED);
    }


    @Override
    public List<BookingResponse> viewBookedDetails(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new ResourceNotFound("user not found");
        }
        List<Booking> bookings = bookingRepository.findAllByUser_UserId(user.getUserId());
        if (bookings.isEmpty()) {
            throw new ResourceNotFound("No booked history for user :" + userName);
        }
        List<BookingResponse> bookingResponses = bookings.stream().map(i -> BookingResponse.builder().movieName(i.getMovie().getMovieName()).userName(i.getUser().getUserName()).totalPrice(i.getTotalPrice()).noOfTickets(i.getNoOfTickets()).purchasedTime(i.getPurchasedTime()).theaterName(i.getTheaterName()).build()).toList();
        return bookingResponses;
    }

    @Override
    public List<BookingResponse> viewBookedDetailsByDate(String userName, LocalDateTime startDate) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new ResourceNotFound("user not found");
        }
        LocalDateTime endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        List<Booking> bookings = bookingRepository.findAllByUser_UserIdAndPurchasedTimeBetween(user.getUserId(), startDate, endDate);
        if (bookings.isEmpty()) {
            throw new ResourceNotFound("No booked history for user :" + userName);
        }
        List<BookingResponse> bookingResponses = bookings.stream().map(i -> BookingResponse.builder().movieName(i.getMovie().getMovieName()).userName(i.getUser().getUserName()).totalPrice(i.getTotalPrice()).noOfTickets(i.getNoOfTickets()).purchasedTime(i.getPurchasedTime()).build()).toList();
        return bookingResponses;
    }
}
