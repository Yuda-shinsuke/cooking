package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.FridgeController;
import com.example.demo.model.Food;
import com.example.demo.model.Fridge;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.FridgeRepository;



@Service
@Transactional
public class FridgeService {

    @Autowired
    private FridgeRepository fridgeRepository;
    private FoodRepository foodRepository;


    public void changeStock(String userName, FridgeController.StockChangeRequest request) {
        // fridgeIdで取得
        Fridge fridge = fridgeRepository.findById(request.getFridgeId())
            .orElseThrow(() -> new IllegalArgumentException("該当の冷蔵庫アイテムがありません"));

        // バージョンチェック（楽観ロック用）
        if (!fridge.getFridgeVersion().equals(request.getFridgeVersion())) {
            throw new IllegalArgumentException("バージョンが一致しません");
        }

        // 更新
        fridge.setFridgeAmount(request.getFridgeAmount());

        // saveは@Transactionalで自動的に反映される
        fridgeRepository.save(fridge);
    }
    
    //在庫追加処理
    public void addAmount(String userName, FridgeController.AddAmountRequest request) {
        Fridge fridge = fridgeRepository
            .findByUserUserNameAndFoodFoodId(userName, request.getFoodId())
            .orElseThrow(() -> new IllegalArgumentException("対象の食材が見つかりません"));

        if (!fridge.getFridgeVersion().equals(request.getFridgeVersion())) {
            throw new IllegalArgumentException("バージョン不一致");
        }

        int newAmount = fridge.getFridgeAmount() + request.getAddAmount();
        fridge.setFridgeAmount(newAmount);
        fridge.setFridgeVersion(fridge.getFridgeVersion() + 1);

        fridgeRepository.save(fridge);
    }
    
    //食材一覧取得
    public List<FridgeController.FoodResponse> getAddableFoods(String userName) {
        List<Food> foods = foodRepository.findAll();

        return foods.stream().map(food -> {
            FridgeController.FoodResponse dto = new FridgeController.FoodResponse();
            dto.setFoodId(food.getFoodId());
            dto.setFoodName(food.getFoodName());
            dto.setFoodHiragana(food.getFoodHiragana());
            dto.setFoodBiggroup(food.getFoodBiggroup());
            dto.setFoodSmallgroup(food.getFoodSmallgroup());
            dto.setFoodUnit(food.getFoodUnit());
            return dto;
        }).collect(Collectors.toList());
    }
}
