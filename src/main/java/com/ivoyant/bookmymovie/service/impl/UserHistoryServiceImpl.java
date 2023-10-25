package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.dto.UserDto;
import com.ivoyant.bookmymovie.dto.WalletDto;
import com.ivoyant.bookmymovie.entity.User;
import com.ivoyant.bookmymovie.exception.ResourceNotFound;
import com.ivoyant.bookmymovie.repository.UserRepository;
import com.ivoyant.bookmymovie.response.BookingResponse;
import com.ivoyant.bookmymovie.response.UserHistoryResponse;
import com.ivoyant.bookmymovie.service.BookingService;
import com.ivoyant.bookmymovie.service.UserHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(UserHistoryServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingService bookingService;

    @Override
    public List<UserHistoryResponse> getHistory(String userName) {
        List<UserHistoryResponse> userHistoryResponses = new ArrayList<>();
        User user = userRepository.findByUserName(userName);
        logger.warn("if user not found throws exception");
        if (user == null) {
            logger.error("user not found");
            throw new ResourceNotFound("user not found");
        }
        List<WalletDto> walletDtoList = user.getWallets().stream().map(i -> WalletDto.builder().walletType(i.getWalletType()).balance(i.getBalance()).build()).toList();
        UserDto userDto = UserDto.builder().userName(user.getUserName()).password(user.getPassword()).role(user.getRole().toString()).walletDtoList(walletDtoList).build();
        List<BookingResponse> bookingResponses = bookingService.viewBookedDetails(userName);
        UserHistoryResponse userHistoryResponse = UserHistoryResponse.builder().userDto(userDto).bookingResponses(bookingResponses).build();
        userHistoryResponses.add(userHistoryResponse);
        logger.info("UserHistory sent successfully");
        return userHistoryResponses;
    }
}
