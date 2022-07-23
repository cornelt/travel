package com.alex.travel.derby.data.service;

import com.alex.travel.derby.data.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    //Travel
    List<Travel> findByTagsContaining(String contains);
}
