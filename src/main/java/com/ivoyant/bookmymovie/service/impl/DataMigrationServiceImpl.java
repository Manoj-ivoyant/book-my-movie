package com.ivoyant.bookmymovie.service.impl;


import com.ivoyant.bookmymovie.Redisrepository.*;
import com.ivoyant.bookmymovie.entity.*;
import com.ivoyant.bookmymovie.model.Theater;
import com.ivoyant.bookmymovie.repository.BookingRepository;
import com.ivoyant.bookmymovie.repository.MovieRepository;
import com.ivoyant.bookmymovie.repository.TheatreRepository;
import com.ivoyant.bookmymovie.repository.TicketSoldRepository;
import com.ivoyant.bookmymovie.repository.UserRepository;
import com.ivoyant.bookmymovie.repository.WalletRepository;
import com.ivoyant.bookmymovie.service.DataMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataMigrationServiceImpl implements DataMigrationService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private TicketSoldRepository ticketSoldRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private TheaterRepo theatreRepo;
    @Autowired
    private TicketSoldRepo ticketSoldRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WalletRepo walletRepo;


    @Override
    public String migrateData() {
        List<Booking> bookingList = bookingRepository.findAll();
        List<com.ivoyant.bookmymovie.model.Booking> bookings = bookingList.stream().map(i -> com.ivoyant.bookmymovie.model.Booking.builder().id(i.getId()).noOfTickets(i.getNoOfTickets()).totalPrice(i.getTotalPrice()).purchasedTime(i.getPurchasedTime()).movieId(i.getMovie().getMovieId()).theatreName(i.getTheaterName()).theatreLocation(i.getTheatreLocation()).build()).toList();
        bookingRepo.saveAll(bookings);
        List<Movie> movieList = movieRepository.findAll();
        List<com.ivoyant.bookmymovie.model.Movie> movies = movieList.stream().map(i -> com.ivoyant.bookmymovie.model.Movie.builder().movieId(i.getMovieId()).movieName(i.getMovieName()).movieLanguage(i.getMovieLanguage()).movieDirector(i.getMovieDirector()).price(i.getPrice()).releasedAt(i.getReleasedAt()).build()).toList();
        movieRepo.saveAll(movies);
        List<Theatre> theatreList = theatreRepository.findAll();
        List<Theater> theaters = theatreList.stream().map(i -> Theater.builder().theatreId(i.getTheatreId()).theatreName(i.getTheatreName()).city(i.getCity()).location(i.getLocation()).noOfSeats(i.getNoOfSeats()).releasedOn(i.getReleasedOn()).movieId(0L).build()).toList();
        theatreRepo.saveAll(theaters);
        List<TicketSold> ticketSolds = ticketSoldRepository.findAll();
        List<com.ivoyant.bookmymovie.model.TicketSold> solds = ticketSolds.stream().map(i -> com.ivoyant.bookmymovie.model.TicketSold.builder().id(i.getId()).ticketsSold(i.getTicketsSold()).theatreId(0L).build()).toList();
        ticketSoldRepo.saveAll(solds);
        List<User> userList = userRepository.findAll();
        List<com.ivoyant.bookmymovie.model.User> users = userList.stream().map(i -> com.ivoyant.bookmymovie.model.User.builder().userId(i.getUserId()).userName(i.getUserName()).password(i.getPassword()).registeredOn(i.getRegisteredOn()).role(i.getRole().toString()).build()).toList();
        userRepo.saveAll(users);
        List<Wallet> walletList = walletRepository.findAll();
        List<com.ivoyant.bookmymovie.model.Wallet> wallets = walletList.stream().map(i -> com.ivoyant.bookmymovie.model.Wallet.builder().walletId(i.getWalletId()).walletType(i.getWalletType()).balance(i.getBalance()).createdAt(i.getCreatedAt()).userId("").build()).toList();
        walletRepo.saveAll(wallets);
        return "migration successfully";
    }
}
