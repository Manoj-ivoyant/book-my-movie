package com.ivoyant.bookmymovie.repository;

import com.ivoyant.bookmymovie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String userName);
}
