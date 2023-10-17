package com.ivoyant.bookmymovie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String movieName;
    private String movieLanguage;
    private String movieDirector;
    private Double price;
    @CreationTimestamp
    private LocalDateTime releasedAt;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Theatre> theatreList;
}
