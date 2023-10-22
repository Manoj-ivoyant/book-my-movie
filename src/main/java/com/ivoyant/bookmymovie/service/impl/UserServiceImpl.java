package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.config.Constants;
import com.ivoyant.bookmymovie.dto.UserDto;
import com.ivoyant.bookmymovie.entity.Role;
import com.ivoyant.bookmymovie.entity.User;
import com.ivoyant.bookmymovie.entity.Wallet;
import com.ivoyant.bookmymovie.exception.ResourceConflictExists;
import com.ivoyant.bookmymovie.model.UserRed;
import com.ivoyant.bookmymovie.model.WalletRed;
import com.ivoyant.bookmymovie.repository.UserRepository;
import com.ivoyant.bookmymovie.response.ApiResponse;
import com.ivoyant.bookmymovie.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate redisTemplate;


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
        List<Wallet> walletList = userDto.getWalletDtoList().stream().map(i -> {
            return Wallet.builder().walletType(i.getWalletType()).balance(i.getBalance()).build();
        }).toList();
        user.setWallets(walletList);
        userRepository.save(user);
        UserRed userRed = exhistingUser(userDto.getUserName());
        if (userRed == null) {
            String id = UUID.randomUUID().toString();
            UserRed userRed2 = UserRed.builder().userId(id).userName(userDto.getUserName()).
                    password(userDto.getPassword()).role(userDto.getRole()).registeredOn(LocalDateTime.now()).build();
            Map<String, UserRed> userRedMap = new HashMap<>();
            userRedMap.put(id, userRed2);
            redisTemplate.opsForHash().putAll(Constants.USER_KEY, userRedMap);
            List<WalletRed> walletReds = walletList.stream().map(i -> WalletRed.builder().walletType(i.getWalletType())
                    .userId(id).balance(i.getBalance()).walletId(new Random().nextLong()).createdAt(LocalDateTime.now())
                    .build()).toList();
            redisTemplate.opsForHash().putAll(Constants.WALLET_KEY, walletReds.stream().collect(Collectors.
                    toMap(WalletRed::getWalletId, Function.identity())));
        } else {
            throw new ResourceConflictExists("user already exists exception");
        }
        return new ApiResponse("user registered successfully", HttpStatus.CREATED);
    }

    public UserRed exhistingUser(String email) {
        List<UserRed> userReds = redisTemplate.opsForHash().values(Constants.USER_KEY);
        return userReds.stream().filter(i -> i.getUserName().equals(email)).findFirst().orElse(null);

    }


}
