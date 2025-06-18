package com.example.foodfindsbackend.controller;

import com.example.foodfindsbackend.model.Recommendation;
import com.example.foodfindsbackend.service.RecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecommendationService recommendationService;

    @Test
    void createRecommendation() throws Exception {
        Recommendation recommendation = new Recommendation("1", "Italian", "Spicy", "Lunch", "Pizza", "123 Main Street");

        mockMvc.perform(post("/recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getAllRecommendations() throws Exception {
        mockMvc.perform(get("/recommendation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateRecommendations() throws Exception {
        Recommendation recommendation = new Recommendation("1", "Indian", "Sweet", "Dinner", "Curry", "456 Oak Street");

        recommendationService.createRecommendation(recommendation);

        recommendation.setCuisine("Japanese");

        mockMvc.perform(put("/recommendation/{id}", recommendation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuisine").value("Japanese"));
    }

    @Test
    void deleteRecommendation() throws Exception {
        Recommendation recommendation = new Recommendation("1", "Mexican", "Savory", "Lunch", "Taco Tienda Mexicana", "31642 John R Rd, Madison Heights, MI 48071");
        recommendationService.createRecommendation(recommendation);

        mockMvc.perform(delete("/recommendation/{id}", recommendation.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/recommendation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getRecommendationById() throws Exception {
        Recommendation recommendation = new Recommendation("1", "Chinese", "Salty", "Dinner", "Noodle House", "789 Maple Avenue");
        recommendationService.createRecommendation(recommendation);

        mockMvc.perform(get("/recommendation/{id}", recommendation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recommendation.getId()))
                .andExpect(jsonPath("$.cuisine").value(recommendation.getCuisine()));
    }

    @Test
    void createRecommendationWithInvalidData() throws Exception {
        Recommendation invalidRecommendation = new Recommendation();

        mockMvc.perform(post("/recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRecommendation)))
                .andExpect(status().isBadRequest());
    }
}
