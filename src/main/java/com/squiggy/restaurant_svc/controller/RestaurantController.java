package com.squiggy.restaurant_svc.controller;

import com.squiggy.api_spec.DTO.*;
import com.squiggy.restaurant_svc.model.DAO.Restaurant;
import com.squiggy.restaurant_svc.service.EventPubSub;
import com.squiggy.restaurant_svc.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    EventPubSub eventPubSub;

    @GetMapping("/getAll")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(@RequestParam double x, @RequestParam double y) {
        try{
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
            System.out.println(restaurantDTOList);
            return new ResponseEntity<>(restaurantDTOList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RestaurantExpandedDTO> getRestaurantById(@PathVariable UUID id) {
        try{
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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orderComplete/{id}")
    public ResponseEntity<Boolean> orderComplete(@PathVariable UUID id) {
        try {
            eventPubSub.produce(false,id.toString(), OrderStatus.READY_FOR_PICKUP);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orderPickedUp/{id}")
    public ResponseEntity<Boolean> orderPickedUp(@PathVariable UUID id) {
        try{
            eventPubSub.produce(false,id.toString(), OrderStatus.PICKED_UP);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
