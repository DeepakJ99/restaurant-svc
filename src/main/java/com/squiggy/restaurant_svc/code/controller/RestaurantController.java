package com.squiggy.restaurant_svc.code.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squiggy.api_spec.DTO.Coords;
import com.squiggy.api_spec.DTO.MenuItemDTO;
import com.squiggy.api_spec.DTO.OrderStatus;
import com.squiggy.api_spec.DTO.RestaurantDTO;
import com.squiggy.api_spec.DTO.RestaurantExpandedDTO;
import com.squiggy.restaurant_svc.code.model.DAO.Restaurant;
import com.squiggy.restaurant_svc.code.service.EventPubSub;
import com.squiggy.restaurant_svc.code.service.RestaurantService;

@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    EventPubSub eventPubSub;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
    @GetMapping("/getAll")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(@RequestParam double x, @RequestParam double y) {
        try{
            logger.info("Trying to get All around {},{}",x,y);
            List<Restaurant> restaurants = restaurantService.getAllRestaurants(x,y);
            List<RestaurantDTO> restaurantDTOList = restaurants.stream().map(res-> {
                RestaurantDTO restaurantDTO = new RestaurantDTO();
                restaurantDTO.setId(res.getId().toString());
                restaurantDTO.setName(res.getName());
                restaurantDTO.setAddress(res.getAddress());
                restaurantDTO.setRating(res.getRating());
                Coords c = new Coords();
                c.setId(res.getCoords().getId().toString());
                c.setX(res.getCoords().getX());
                c.setY(res.getCoords().getY());
                restaurantDTO.setCoords(c);
                restaurantDTO.setActive(res.getActive());
                return restaurantDTO;
            }).toList();
            return new ResponseEntity<>(restaurantDTOList, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Error getting all restaurants {},{}", e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RestaurantExpandedDTO> getRestaurantById(@PathVariable UUID id) {
        try{
            logger.info("getting restaurant by id {}", id);
            Restaurant restaurant = restaurantService.getRestaurant(id);
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setId(restaurant.getId().toString());
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setAddress(restaurant.getAddress());
            restaurantDTO.setRating(restaurant.getRating());
            Coords coords = new Coords();
            coords.setId(restaurant.getCoords().getId().toString());
            coords.setX(restaurant.getCoords().getX());
            coords.setY(restaurant.getCoords().getY());
            restaurantDTO.setCoords(coords);
            restaurantDTO.setActive(restaurant.getActive());
            RestaurantExpandedDTO restaurantExpandedDTO = new RestaurantExpandedDTO();
            restaurantExpandedDTO.setRestaurant(restaurantDTO);
            restaurantExpandedDTO.setMenuItems(restaurant.getMenuItemList().stream().map(menuItem ->{
                MenuItemDTO menuItemDTO = new MenuItemDTO();
                menuItemDTO.setId(menuItem.getId().toString());
                menuItemDTO.setName(menuItem.getName());
                menuItemDTO.setPrice(menuItem.getPrice());
                menuItemDTO.setRating(menuItem.getRating());
                menuItemDTO.setDescription(menuItem.getDescription());
                menuItemDTO.setOutOfStock(menuItem.getOutOfStock());
                return menuItemDTO;
            }).toList());
            return new ResponseEntity<>(restaurantExpandedDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting restaurant id {} {}", e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orderComplete/{id}")
    public ResponseEntity<Boolean> orderComplete(@PathVariable UUID id) {
        try {
            logger.info("Order with id {} : completed", id);
            eventPubSub.produce(false,id.toString(), OrderStatus.READY_FOR_PICKUP);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Error completing order with id {} {} {}", id, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orderPickedUp/{id}")
    public ResponseEntity<Boolean> orderPickedUp(@PathVariable UUID id) {
        try{
            logger.info("Order with id {} picked up",id);
            eventPubSub.produce(false,id.toString(), OrderStatus.PICKED_UP);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.info("Error marking order with id {} picked up", id);
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
