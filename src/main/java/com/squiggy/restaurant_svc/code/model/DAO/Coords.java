package com.squiggy.restaurant_svc.code.model.DAO;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

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
