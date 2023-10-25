package com.ivoyant.bookmymovie.repository;

import com.ivoyant.bookmymovie.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Theatre findByTheatreNameAndLocation(String theatreName,String Location);

    List<Theatre> findAllByTheatreName(String theatreName);

    Theatre findByTheatreName(String theatreName);
}
