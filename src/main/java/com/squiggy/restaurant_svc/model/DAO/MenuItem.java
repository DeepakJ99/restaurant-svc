package com.squiggy.restaurant_svc.model.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column
    private Double rating;

    @ManyToOne
    private Restaurant restaurant;

    @Column
    private Boolean outOfStock;

}
