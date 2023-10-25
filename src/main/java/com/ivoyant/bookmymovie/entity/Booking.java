package com.ivoyant.bookmymovie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer noOfTickets;
    private Double totalPrice;
    @CreationTimestamp
    private LocalDateTime purchasedTime;
    @ManyToOne
    private User user;
    @ManyToOne
    private Movie movie;
    private String theaterName;
    private String theatreLocation;
}
