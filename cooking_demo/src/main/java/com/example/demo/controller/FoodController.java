package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Food;
import com.example.demo.repository.FoodRepository;

@RestController
@RequestMapping("/{userName}/stock")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    //  一覧表示
    @GetMapping
    @CrossOrigin
    public List<Food> getAllFoods() {
        return foodRepository.findAll(Sort.by(Sort.Direction.ASC, "foodId"));
    }


}