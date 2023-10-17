package com.ivoyant.bookmymovie.controller;

import com.ivoyant.bookmymovie.response.UserHistoryResponse;
import com.ivoyant.bookmymovie.service.UserHistoryService;
import com.ivoyant.bookmymovie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserHistoryController {
    @Autowired
    private UserHistoryService userHistoryService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<UserHistoryResponse>> userHistory(@PathVariable String userName){
        return ResponseEntity.ok().body(userHistoryService.getHistory(userName));

    }

}
