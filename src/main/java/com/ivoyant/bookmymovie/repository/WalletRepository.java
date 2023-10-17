package com.ivoyant.bookmymovie.repository;

import com.ivoyant.bookmymovie.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
