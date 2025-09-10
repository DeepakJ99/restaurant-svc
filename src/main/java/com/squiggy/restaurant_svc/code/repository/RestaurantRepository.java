package com.squiggy.restaurant_svc.code.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squiggy.restaurant_svc.code.model.DAO.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

}
