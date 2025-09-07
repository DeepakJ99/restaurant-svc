package com.squiggy.restaurant_svc.service;

import com.squiggy.restaurant_svc.model.DAO.Restaurant;
import com.squiggy.restaurant_svc.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
