package com.ivoyant.bookmymovie.dto;

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
public class WalletDto {
    @NotBlank(message = "required field")
    private String walletType;
    @NotNull(message = "required field")
    @Min(value = 0, message = "must be greater or equals to zero")
    private Double balance;
}
