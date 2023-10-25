package com.ivoyant.bookmymovie.Redisrepository;

import com.ivoyant.bookmymovie.model.TicketSoldRed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSoldRepo extends CrudRepository<TicketSoldRed, Long> {
}
