package com.ivoyant.bookmymovie.service;

import com.ivoyant.bookmymovie.response.UserHistoryResponse;

import java.util.List;

public interface UserHistoryService {
    List<UserHistoryResponse> getHistory(String userName);
}
