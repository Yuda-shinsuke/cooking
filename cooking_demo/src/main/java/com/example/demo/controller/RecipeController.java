package com.example.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Food;
import com.example.demo.model.Recipe;
import com.example.demo.repository.FoodRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/{userId}/recipes")
public class RecipeController {

    @Autowired
    private FoodRepository foodRepository;

    @PostMapping("/generate")
    @CrossOrigin
    public ResponseEntity<List<Recipe>> generateRecipes(@RequestBody List<Integer> foodIds) {
        try {
            // foodId → 食材名への変換
            List<Food> foodList = foodRepository.findByFoodIdIn(foodIds);
            List<String> ingredientNames = foodList.stream()
                                                   .map(Food::getFoodName)
                                                   .collect(Collectors.toList());

            // プロンプト作成
            String prompt = buildPrompt(ingredientNames);

            // Ollama呼び出し
            String rawResponse = callOllamaAPI(prompt);

            // JSON整形（Jackson）
            ObjectMapper mapper = new ObjectMapper();
            List<Recipe> recipes = mapper.readValue(rawResponse, new TypeReference<List<Recipe>>() {});

            return ResponseEntity.ok(recipes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String buildPrompt(List<String> ingredientNames) {
        return String.format("""
            以下の材料で作れる料理を最大10個提案してください。
            以下の形式のJSONで出力してください：

            [{
              "title": "料理名",
              "description": "料理の概要",
              "ingredients": [foodIdの整数配列],
              "steps": ["手順1", "手順2"],
              "quantity": { foodId: 数量（整数） }
            }]

            材料: %s
            調味料はある前提で構いません。
            食材は食材名ではなくfoodIdに変換して返してください。
            """, String.join("、", ingredientNames));
    }

    private String callOllamaAPI(String prompt) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format("""
            {
                "model": "hf.co/mmnga/tokyotech-llm-Llama-3.1-Swallow-8B-Instruct-v0.3-gguf:latest",
                "prompt": "%s"
            }
            """, prompt.replace("\"", "\\\""));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://172.16.0.132:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return extractResponse(response.body());
    }

    private String extractResponse(String body) {
        StringBuilder sb = new StringBuilder();
        for (String line : body.split("\n")) {
            if (line.contains("\"response\"")) {
                int start = line.indexOf("\"response\":\"") + 12;
                int end = line.indexOf("\"", start);
                if (start >= 0 && end > start) {
                    sb.append(line, start, end);
                }
            }
        }
        return sb.toString();
    }
}
