package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.FridgeService;

@RestController
public class FridgeController {

    @Autowired
    private FridgeService fridgeService;

    // 冷蔵庫の中身を変更
    @PostMapping("/{userName}/stock/change")
    @CrossOrigin
    public ResponseEntity<String> updateStock(
        @PathVariable String userName,
        @RequestBody StockChangeRequest request) {

        try {
            fridgeService.changeStock(userName, request);
            return ResponseEntity.ok("在庫変更成功");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("処理中にエラーが発生しました");
        }
    }

    // 買ってきた食材を冷蔵庫に追加
    @PostMapping("/{userName}/addAmount")
    @CrossOrigin
    public ResponseEntity<String> addAmount(
        @PathVariable String userName,
        @RequestBody AddAmountRequest request) {

        try {
            fridgeService.addAmount(userName, request);
            return ResponseEntity.ok("食材追加成功");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("処理中にエラーが発生しました");
        }
    }

    // 食材一覧取得
    @GetMapping("/{userName}/add")
    @CrossOrigin
    public ResponseEntity<List<FoodResponse>> getAddableFoods(@PathVariable String userName) {
        try {
            List<FoodResponse> foods = fridgeService.getAddableFoods(userName);
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public static class StockChangeRequest {
        private Integer fridgeId;
        private Integer fridgeAmount;
        private Integer fridgeVersion;

        public Integer getFridgeId() { return fridgeId; }
        public void setFridgeId(Integer fridgeId) { this.fridgeId = fridgeId; }
        public Integer getFridgeAmount() { return fridgeAmount; }
        public void setFridgeAmount(Integer fridgeAmount) { this.fridgeAmount = fridgeAmount; }
        public Integer getFridgeVersion() { return fridgeVersion; }
        public void setFridgeVersion(Integer fridgeVersion) { this.fridgeVersion = fridgeVersion; }
    }

    public static class AddAmountRequest {
        private Integer foodId;
        private Integer addAmount;
        private Integer fridgeVersion;

        public Integer getFoodId() { return foodId; }
        public void setFoodId(Integer foodId) { this.foodId = foodId; }
        public Integer getAddAmount() { return addAmount; }
        public void setAddAmount(Integer addAmount) { this.addAmount = addAmount; }
        public Integer getFridgeVersion() { return fridgeVersion; }
        public void setFridgeVersion(Integer fridgeVersion) { this.fridgeVersion = fridgeVersion; }
    }

    public static class FoodResponse {
        private Integer foodId;
        private String foodName;
        private String foodHiragana;
        private String foodBiggroup;
        private String foodSmallgroup;
        private String foodUnit;

        public Integer getFoodId() { return foodId; }
        public void setFoodId(Integer foodId) { this.foodId = foodId; }

        public String getFoodName() { return foodName; }
        public void setFoodName(String foodName) { this.foodName = foodName; }

        public String getFoodHiragana() { return foodHiragana; }
        public void setFoodHiragana(String foodHiragana) { this.foodHiragana = foodHiragana; }

        public String getFoodBiggroup() { return foodBiggroup; }
        public void setFoodBiggroup(String foodBiggroup) { this.foodBiggroup = foodBiggroup; }

        public String getFoodSmallgroup() { return foodSmallgroup; }
        public void setFoodSmallgroup(String foodSmallgroup) { this.foodSmallgroup = foodSmallgroup; }

        public String getFoodUnit() { return foodUnit; }
        public void setFoodUnit(String foodUnit) { this.foodUnit = foodUnit; }
    }
}
