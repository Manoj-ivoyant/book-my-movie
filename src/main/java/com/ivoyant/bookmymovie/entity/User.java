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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String userId;
    @Column(unique = true)
    private String userName;
    private String password;
    private Role role;
    @CreationTimestamp
    private LocalDateTime registeredOn;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Wallet> wallets;
}
