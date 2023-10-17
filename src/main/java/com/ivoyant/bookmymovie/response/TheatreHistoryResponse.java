package com.ivoyant.bookmymovie.response;

import com.ivoyant.bookmymovie.dto.TheatreDto;
import com.ivoyant.bookmymovie.entity.TicketSold;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreHistoryResponse {
    private TheatreDto theatreDto;
    private List<BookingResponse> bookingResponses;
    private Integer ticketSold;
}
