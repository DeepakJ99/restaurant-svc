package com.squiggy.restaurant_svc.config;


import com.squiggy.restaurant_svc.model.DAO.Coords;
import com.squiggy.restaurant_svc.model.DAO.MenuItem;
import com.squiggy.restaurant_svc.model.DAO.Restaurant;
import com.squiggy.restaurant_svc.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(RestaurantRepository restaurantRepository) {
        return args -> {

            // -----------------------------
            // Restaurant 1 - Green Leaf
            // -----------------------------
            Restaurant r1 = new Restaurant();
            r1.setName("Green Leaf");
            r1.setAddress("123 MG Road, Bangalore");
            r1.setRating(4.5);
            r1.setActive(true);
            r1.setIsVegetarian(true);
            r1.setCapacity(5);

            Coords c1 = new Coords();
            c1.setX(12.9716);
            c1.setY(77.5946);
            c1.setRestaurant(r1);
            r1.setCoords(c1);

            MenuItem r1m1 = new MenuItem();
            r1m1.setName("Paneer Butter Masala");
            r1m1.setDescription("Delicious paneer curry");
            r1m1.setPrice(250.0);
            r1m1.setRating(4.7);
            r1m1.setOutOfStock(false);
            r1m1.setRestaurant(r1);

            MenuItem r1m2 = new MenuItem();
            r1m2.setName("Veg Biryani");
            r1m2.setDescription("Fragrant vegetable biryani");
            r1m2.setPrice(180.0);
            r1m2.setRating(4.5);
            r1m2.setOutOfStock(false);
            r1m2.setRestaurant(r1);

            MenuItem r1m3 = new MenuItem();
            r1m3.setName("Dal Makhani");
            r1m3.setDescription("Creamy lentil curry");
            r1m3.setPrice(150.0);
            r1m3.setRating(4.6);
            r1m3.setOutOfStock(false);
            r1m3.setRestaurant(r1);

            MenuItem r1m4 = new MenuItem();
            r1m4.setName("Aloo Gobi");
            r1m4.setDescription("Spiced potatoes and cauliflower");
            r1m4.setPrice(120.0);
            r1m4.setRating(4.3);
            r1m4.setOutOfStock(false);
            r1m4.setRestaurant(r1);

            MenuItem r1m5 = new MenuItem();
            r1m5.setName("Mixed Veg Curry");
            r1m5.setDescription("Seasonal vegetables in curry");
            r1m5.setPrice(200.0);
            r1m5.setRating(4.4);
            r1m5.setOutOfStock(false);
            r1m5.setRestaurant(r1);

            r1.setMenuItemList(Arrays.asList(r1m1, r1m2, r1m3, r1m4, r1m5));


            // -----------------------------
            // Restaurant 2 - Spicy Treat
            // -----------------------------
            Restaurant r2 = new Restaurant();
            r2.setName("Spicy Treat");
            r2.setAddress("456 Connaught Place, Delhi");
            r2.setRating(4.2);
            r2.setActive(true);
            r2.setIsVegetarian(false);
            r2.setCapacity(10);

            Coords c2 = new Coords();
            c2.setX(28.7041);
            c2.setY(77.1025);
            r2.setCoords(c2);
            c2.setRestaurant(r2);

            MenuItem r2m1 = new MenuItem();
            r2m1.setName("Chicken Tikka");
            r2m1.setDescription("Spicy grilled chicken");
            r2m1.setPrice(350.0);
            r2m1.setRating(4.6);
            r2m1.setOutOfStock(false);
            r2m1.setRestaurant(r2);

            MenuItem r2m2 = new MenuItem();
            r2m2.setName("Mutton Curry");
            r2m2.setDescription("Rich mutton curry");
            r2m2.setPrice(400.0);
            r2m2.setRating(4.8);
            r2m2.setOutOfStock(false);
            r2m2.setRestaurant(r2);

            MenuItem r2m3 = new MenuItem();
            r2m3.setName("Butter Chicken");
            r2m3.setDescription("Classic North Indian dish");
            r2m3.setPrice(380.0);
            r2m3.setRating(4.7);
            r2m3.setOutOfStock(false);
            r2m3.setRestaurant(r2);

            MenuItem r2m4 = new MenuItem();
            r2m4.setName("Paneer Tikka");
            r2m4.setDescription("Grilled paneer cubes");
            r2m4.setPrice(270.0);
            r2m4.setRating(4.5);
            r2m4.setOutOfStock(false);
            r2m4.setRestaurant(r2);

            MenuItem r2m5 = new MenuItem();
            r2m5.setName("Veg Korma");
            r2m5.setDescription("Mixed veggies in creamy sauce");
            r2m5.setPrice(220.0);
            r2m5.setRating(4.4);
            r2m5.setOutOfStock(false);
            r2m5.setRestaurant(r2);

            r2.setMenuItemList(Arrays.asList(r2m1, r2m2, r2m3, r2m4, r2m5));


            // -----------------------------
            // Similarly, build r3 (Seafood Delight), r4 (South Spice), r5 (Bengal Feast)
            // -----------------------------

            restaurantRepository.saveAll(Arrays.asList(r1, r2 /*, r3, r4, r5 */));
        };
    }
}
