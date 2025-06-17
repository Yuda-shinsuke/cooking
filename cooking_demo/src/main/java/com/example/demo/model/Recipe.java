package com.example.demo.model;

import java.util.List;
import java.util.Map;

public class Recipe {
    private String title; // 料理名
    private String description; // 料理の概要説明
    private List<Integer> ingredients; // 材料ID
    private List<String> steps; // 調理工程
    private Map<Integer, Integer> quantity; // 材料の個数

    // 空のコンストラクタ
    public Recipe() {}

    // Getter & Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Map<Integer, Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(Map<Integer, Integer> quantity) {
        this.quantity = quantity;
    }
}
