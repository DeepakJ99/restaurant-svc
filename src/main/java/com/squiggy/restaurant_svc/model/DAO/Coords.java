package com.squiggy.restaurant_svc.model.DAO;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Coords {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column(nullable = false)
    private double x;
    @Column(nullable = false)
    private double y;

    @OneToOne(cascade = CascadeType.ALL)
    Restaurant restaurant;
}
