package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.UserDto;
import com.ivoyant.bookmymovie.entity.Role;
import com.ivoyant.bookmymovie.entity.User;
import com.ivoyant.bookmymovie.repository.UserRepository;
import com.ivoyant.bookmymovie.response.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterSuccess(){
        UserDto userDto=UserDto.builder().userName("manoj@gmail.com").password("Man@2209").role("user").build();
        User user=User.builder().userName("manoj@gmail.com").password("Man@2209").role(Role.USER).build();
        ApiResponse apiResponse=userService.registerUser(userDto);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assertions.assertEquals("user registered successfully",apiResponse.getMessage());
        Assertions.assertEquals(HttpStatus.CREATED,apiResponse.getStatus());

    }
}
