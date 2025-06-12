package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

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

     @Column(name = "food_group")  // 食品名のカラム
    private String foodGroup;
     
     @Version
     @Column(name = "version")    // バージョンのカラム
     private Integer version;
}