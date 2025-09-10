package com.squiggy.restaurant_svc.code.model.DAO;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

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
