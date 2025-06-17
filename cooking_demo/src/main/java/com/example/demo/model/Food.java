package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "food")  // DBのテーブル名
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")  // 主キーのカラム名
    private Integer foodId;

    @Column(name = "food_name")  // 食品名のカラム
    private String foodName;

     @Column(name = "food_hiragana")  // 食品名の平仮名のカラム
    private String foodHiragana;
     
     @Column(name = "biggroup_id")  // 食品の大分類のカラム
    private String foodBiggroup;
     
     @Column(name = "smallgroup_id")  // 食品名の小分類のカラム
    private String foodSmallgroup;
     
     @Column(name = "food_unit")  // 食品の単位のカラム
    private String foodUnit;
     
     @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
     @JsonManagedReference
     private List<Fridge> fridges;


}