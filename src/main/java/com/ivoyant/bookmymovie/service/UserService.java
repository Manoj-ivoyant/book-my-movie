package com.ivoyant.bookmymovie.service;

import com.ivoyant.bookmymovie.dto.UserDto;
import com.ivoyant.bookmymovie.response.ApiResponse;

public interface UserService {
    ApiResponse registerUser(UserDto userDto);
}
