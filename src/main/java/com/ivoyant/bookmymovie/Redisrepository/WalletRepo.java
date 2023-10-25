package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.WalletRed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends CrudRepository<WalletRed,Long> {
}
