package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.UserDto;
import com.ivoyant.bookmymovie.entity.Role;
import com.ivoyant.bookmymovie.entity.User;
import com.ivoyant.bookmymovie.entity.Wallet;
import com.ivoyant.bookmymovie.repository.UserRepository;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public ApiResponse registerUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setUserId(UUID.randomUUID().toString());
        user.setRole(Role.ADMIN);
        if ("User".equalsIgnoreCase(userDto.getRole())) {
            user.setRole(Role.USER);
        }
        user.setPassword(userDto.getPassword());
        List<Wallet> walletList=userDto.getWalletDtoList().stream().map(i->{
            return Wallet.builder().walletType(i.getWalletType()).balance(i.getBalance()).build();
        }).toList();
        user.setWallets(walletList);
        userRepository.save(user);
        return new ApiResponse("user registered successfully", HttpStatus.CREATED);
    }

}
