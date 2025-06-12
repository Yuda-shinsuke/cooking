package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Food;
import com.example.demo.repository.FoodRepository;

@RestController
@RequestMapping("/api/stock")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    // 1. 一覧表示: GET /api/stock
    @GetMapping
    @CrossOrigin
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // 2. 買い物追加: POST /api/stock/buy
    @PostMapping("/buy")
    @CrossOrigin
    public Food buyFood(@RequestBody Food food) {
        return foodRepository.save(food);
    }

    // 3. 手動追加: POST /api/stock/addfood
    @PostMapping("/addfood")
    @CrossOrigin
    public Food addNewFood(@RequestBody Food food) {
        return foodRepository.save(food);
    }

}