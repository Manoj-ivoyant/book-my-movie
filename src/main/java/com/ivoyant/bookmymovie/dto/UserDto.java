package com.ivoyant.bookmymovie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @Email(message = "invalid email")
    @NotBlank
    private String userName;
    @NotBlank(message = "specify password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$", message = "Password must contain atleast 1 uppercase letter, 1 lowercase letter,  1 special character, 1 number, Min 8 characters.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^(?:Admin|User|admin|user)$", message = "specify proper role")
    private String role;
    private List<WalletDto> walletDtoList;
}
