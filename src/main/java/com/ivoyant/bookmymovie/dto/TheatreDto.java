package com.ivoyant.bookmymovie.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TheatreDto {
    @NotBlank(message = "required field")
    private String theatreName;
    @NotBlank(message = "required field")
    private String location;
    @NotBlank(message = "required field")
    private String city;
    @NotBlank(message = "required field")
    @Max(value = 200, message = "maximum allowed is 200")
    private Integer noOfSeats;
}
