package com.ivoyant.bookmymovie.service;

import com.ivoyant.bookmymovie.response.TheatreHistoryResponse;

import java.util.List;

public interface TheatreHistoryService {
    List<TheatreHistoryResponse> getTheatreHistory(String theatreName);
}
