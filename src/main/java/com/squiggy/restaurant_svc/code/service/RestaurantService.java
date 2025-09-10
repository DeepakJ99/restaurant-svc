package com.squiggy.restaurant_svc.code.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.squiggy.restaurant_svc.code.model.DAO.Restaurant;
import com.squiggy.restaurant_svc.code.repository.RestaurantRepository;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants(double x, double y) {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(UUID id) {
        return restaurantRepository.getReferenceById(id);
    }
}
