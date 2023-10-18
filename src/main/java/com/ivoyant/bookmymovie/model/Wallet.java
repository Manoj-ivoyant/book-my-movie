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
@RedisHash("Wallet")
public class Wallet implements Serializable {
    @Id
    private Long walletId;
    private String walletType;
    private Double balance;
    @CreatedDate
    private LocalDateTime createdAt;
    private String userId;
}
