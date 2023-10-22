package com.ivoyant.bookmymovie.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "TicketSold",timeToLive = 86400L)
public class TicketSoldRed implements Serializable {
    @Id
    private Long id;
    private Long theatreId;
    private Integer ticketsSold;
}
