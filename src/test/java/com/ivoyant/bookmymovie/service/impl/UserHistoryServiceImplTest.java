package com.ivoyant.bookmymovie.service.impl;

import com.ivoyant.bookmymovie.repository.UserRepository;
import com.ivoyant.bookmymovie.service.BookingService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserHistoryServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingService bookingService;

    @InjectMocks
    private UserHistoryServiceImpl userHistoryServiceImpl;


}
