package com.ivoyant.bookmymovie.dto;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    @NotBlank(message = "movieName is required field")
    private String movieName;
    @NotBlank(message = "language is required field")
    private String movieLanguage;
    @NotBlank(message = "director is required field")
    private String movieDirector;
    @Min(value = 100,message = "movie price must be 100 or greater")
    private Double price;
    @OneToMany
    private List<TheatreDto> theatreDtoList;
}
