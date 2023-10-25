package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.TheatreDto;
import com.ivoyant.bookmymovie.entity.Booking;
import com.ivoyant.bookmymovie.entity.Theatre;
import com.ivoyant.bookmymovie.entity.TicketSold;
import com.ivoyant.bookmymovie.exception.ResourceNotFound;
import com.ivoyant.bookmymovie.repository.BookingRepository;
import com.ivoyant.bookmymovie.repository.TheatreRepository;
import com.ivoyant.bookmymovie.repository.TicketSoldRepository;
import com.ivoyant.bookmymovie.response.BookingResponse;
import com.ivoyant.bookmymovie.response.TheatreHistoryResponse;
import com.ivoyant.bookmymovie.service.TheatreHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheatreHistoryServiceImpl implements TheatreHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(TheatreHistoryServiceImpl.class);
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private TicketSoldRepository ticketSoldRepository;

    @Override
    public List<TheatreHistoryResponse> getTheatreHistory(String theatreName) {
        List<Theatre> theatreList = theatreRepository.findAllByTheatreName(theatreName);
        logger.warn("if theatreList is null it may throw ResourceNotFound Exception");
        if (theatreList.isEmpty()) {
            logger.error("empty list of theatres");
            throw new ResourceNotFound("no such theatre found");
        }
        List<TheatreHistoryResponse> theatreHistoryResponses = new ArrayList<>();
        List<TheatreDto> theatreDtoList = theatreList.stream().map(i -> TheatreDto.builder().theatreName(i.getTheatreName()).city(i.getCity()).location(i.getLocation()).noOfSeats(i.getNoOfSeats()).build()).toList();
        theatreDtoList.forEach(i -> {
            List<Booking> bookings = bookingRepository.findAllByTheaterNameAndTheatreLocation(i.getTheatreName(), i.getLocation());
            List<BookingResponse> bookingResponses = bookings.stream().map(j -> BookingResponse.builder().movieName(j.getMovie().getMovieName()).userName(j.getUser().getUserName()).totalPrice(j.getTotalPrice()).noOfTickets(j.getNoOfTickets()).purchasedTime(j.getPurchasedTime()).theaterName(j.getTheaterName()).build()).toList();
            TheatreHistoryResponse theatreHistoryResponse;
            TicketSold ticketSold = ticketSoldRepository.findByTheaterNameAndTheatreLocation(i.getTheatreName(), i.getLocation());

            if (ticketSold == null) {
                logger.warn("no tickets has sold for the theater");
                theatreHistoryResponse = TheatreHistoryResponse.builder().theatreDto(i).bookingResponses(bookingResponses).ticketSold(0).build();
            } else {
                theatreHistoryResponse = TheatreHistoryResponse.builder().theatreDto(i).bookingResponses(bookingResponses).ticketSold(ticketSold.getTicketsSold()).build();
            }

            theatreHistoryResponses.add(theatreHistoryResponse);

        });
        logger.info("Theater History  sent successfully");
        return theatreHistoryResponses;
    }
}
