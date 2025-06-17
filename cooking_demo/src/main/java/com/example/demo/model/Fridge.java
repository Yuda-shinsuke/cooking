package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "fridge")
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_id")
    private Integer fridgeId;

    @Column(name = "fridge_amount")
    private Integer fridgeAmount;

    @Version
    @Column(name = "fridge_version")
    private Integer fridgeVersion;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    @JsonBackReference
    private Food food;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
