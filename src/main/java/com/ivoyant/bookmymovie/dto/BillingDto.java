package com.ivoyant.bookmymovie.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingDto {
    @NotBlank(message = "required field")
    private String movieName;
    @NotBlank(message = "required field")
    private String movieLanguage;
    @NotBlank(message = "required field")
    private String theatreName;
    @NotBlank(message = "required field")
    private String location;
    @NotNull(message = "required field")
    @Min(value = 1, message = "select atleast 1 seat ")
    @Max(value = 10, message = "maximum 10 seats allowed")
    private Integer noOfSeats;
}
