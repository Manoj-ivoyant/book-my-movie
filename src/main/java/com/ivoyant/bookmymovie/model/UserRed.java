package com.ivoyant.bookmymovie.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "User",timeToLive = 86400L)
public class UserRed implements Serializable {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String role;
    @CreatedDate
    private LocalDateTime registeredOn;
}
