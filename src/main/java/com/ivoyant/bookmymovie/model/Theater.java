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
@RedisHash("Theater")
public class Theater implements Serializable {
    @Id
    private Long theatreId;
    private String theatreName;
    private String location;
    private String city;
    private Integer noOfSeats;
    @CreatedDate
    private LocalDateTime releasedOn;
    private Long movieId;
}
