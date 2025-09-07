package com.squiggy.restaurant_svc.repository;

import com.squiggy.restaurant_svc.model.DAO.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

}
