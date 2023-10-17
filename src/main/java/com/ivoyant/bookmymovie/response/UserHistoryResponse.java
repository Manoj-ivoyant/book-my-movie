package com.ivoyant.bookmymovie.response;

import com.ivoyant.bookmymovie.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserHistoryResponse {
    private UserDto userDto;
    private List<BookingResponse> bookingResponses;


}
