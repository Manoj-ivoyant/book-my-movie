package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.UserRed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserRed,String> {
}
