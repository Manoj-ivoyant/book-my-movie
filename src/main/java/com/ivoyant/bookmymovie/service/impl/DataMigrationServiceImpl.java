package com.ivoyant.bookmymovie.service.impl;


import com.ivoyant.bookmymovie.Redisrepository.*;
import com.ivoyant.bookmymovie.config.Constants;
import com.ivoyant.bookmymovie.entity.*;
import com.ivoyant.bookmymovie.model.*;
import com.ivoyant.bookmymovie.repository.*;
import com.ivoyant.bookmymovie.service.DataMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String migrateData() {
        List<Booking> bookingList = bookingRepository.findAll();
        List<BookingRed> bookings = bookingList.stream()
                .map(i -> BookingRed.builder().id(i.getId()).noOfTickets(i.getNoOfTickets()).
                        totalPrice(i.getTotalPrice()).purchasedTime(i.getPurchasedTime()).movieId(i.getMovie()
                                .getMovieId()).theatreName(i.getTheaterName()).theatreLocation(i.getTheatreLocation())
                        .build()).toList();
        Map<Long, BookingRed> bookingRedMap = bookings.stream().collect(Collectors.toMap(BookingRed::getId,
                Function.identity()));
        redisTemplate.opsForHash().putAll(Constants.BOOKING_KEY, bookingRedMap);

        List<Movie> movieList = movieRepository.findAll();
        List<MovieRed> movies = movieList.stream().map(i -> MovieRed.builder().movieId(i.getMovieId())
                .movieName(i.getMovieName()).movieLanguage(i.getMovieLanguage()).movieDirector(i.getMovieDirector())
                .price(i.getPrice()).releasedAt(i.getReleasedAt()).build()).toList();
        Map<Long, MovieRed> movieRedMap = movies.stream().collect(Collectors.toMap(MovieRed::getMovieId, Function
                .identity()));
        redisTemplate.opsForHash().putAll(Constants.MOVIE_KEY, movieRedMap);

        List<Theatre> theatreList = theatreRepository.findAll();
        List<TheaterRed> theaters = theatreList.stream().map(i -> TheaterRed.builder().theatreId(i.getTheatreId())
                .theatreName(i.getTheatreName()).city(i.getCity()).location(i.getLocation()).noOfSeats(i.getNoOfSeats())
                .releasedOn(i.getReleasedOn()).movieId(0L).build()).toList();
        Map<Long, TheaterRed> theaterRedMap = theaters.stream().collect(Collectors.toMap(TheaterRed::getTheatreId,
                Function.identity()));
        redisTemplate.opsForHash().putAll(Constants.THEATER_KEY, theaterRedMap);

        List<TicketSold> ticketSolds = ticketSoldRepository.findAll();
        List<TicketSoldRed> solds = ticketSolds.stream().map(i -> TicketSoldRed.builder().id(i.getId())
                .ticketsSold(i.getTicketsSold()).theatreId(0L).build()).toList();
        Map<Long, TicketSoldRed> ticketSoldRedMap = solds.stream().collect(Collectors.toMap(TicketSoldRed::getId,
                Function.identity()));
        redisTemplate.opsForHash().putAll(Constants.TICKETSOLD_KEY, ticketSoldRedMap);

        List<User> userList = userRepository.findAll();
        List<UserRed> users = userList.stream().map(i -> UserRed.builder().userId(i.getUserId()).
                userName(i.getUserName()).password(i.getPassword()).registeredOn(i.getRegisteredOn()).role(i.getRole()
                        .toString()).build()).toList();
        Map<String, UserRed> userRedMap = users.stream().collect(Collectors.toMap(UserRed::getUserId,
                Function.identity()));
        redisTemplate.opsForHash().putAll(Constants.USER_KEY, userRedMap);

        List<Wallet> walletList = walletRepository.findAll();
        List<WalletRed> wallets = walletList.stream().map(i -> WalletRed.builder().walletId(i.getWalletId())
                .walletType(i.getWalletType()).balance(i.getBalance()).createdAt(i.getCreatedAt()).userId("")
                .build()).toList();
        Map<Long, WalletRed> walletRedMap = wallets.stream().collect(Collectors.toMap(WalletRed::getWalletId,
                Function.identity()));
        redisTemplate.opsForHash().putAll(Constants.WALLET_KEY, walletRedMap);
        return "migration successfully";
    }
}
