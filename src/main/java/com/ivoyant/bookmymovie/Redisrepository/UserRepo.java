package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,String> {
}
