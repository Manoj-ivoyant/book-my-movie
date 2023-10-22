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
@RedisHash(value = "Movie",timeToLive = 86400L)
public class MovieRed implements Serializable {
    @Id
    private Long movieId;
    private String movieName;
    private String movieLanguage;
    private String movieDirector;
    private Double price;
    @CreatedDate
    private LocalDateTime releasedAt;
}
