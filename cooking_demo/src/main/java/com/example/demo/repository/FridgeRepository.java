package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Fridge;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Integer> {
	Optional<Fridge> findByUserUserNameAndFoodFoodId(String userName, Integer foodId);
}

