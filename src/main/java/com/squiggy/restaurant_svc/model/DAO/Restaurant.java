package com.squiggy.restaurant_svc.model.DAO;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    private Coords coords;

    @Column
    private Double rating;

    @Column
    private Boolean active;

    @Column
    private Boolean isVegetarian;

    // capacity is number of dishes that can be prepared simualtaneously,
    // if incoming number of orders > capacity, put restaurant inactive
    @Column
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL)
    List<MenuItem> menuItemList;

}
