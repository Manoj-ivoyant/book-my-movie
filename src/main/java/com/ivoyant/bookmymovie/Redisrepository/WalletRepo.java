package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends CrudRepository<Wallet,Long> {
}
