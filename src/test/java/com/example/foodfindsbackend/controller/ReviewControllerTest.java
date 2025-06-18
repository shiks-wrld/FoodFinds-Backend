package com.example.foodfindsbackend.controller;

import com.example.foodfindsbackend.model.Review;
import com.example.foodfindsbackend.service.ReviewService;
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
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewService reviewService;

    @Test
    void createReview() throws Exception {
        Review review = new Review("1", "123 Main St", "Test Location", "Italian", 4, "Good", "Nice place");

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getAllReviews() throws Exception {
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateReview() throws Exception {
        Review review = new Review("1", "456 Oak St", "Updated Location", "Japanese", 5, "Excellent", "Amazing experience");

        reviewService.createReview(review);

        review.setCuisine("Korean");

        mockMvc.perform(put("/reviews/{id}", review.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuisine").value("Korean"));
    }

    @Test
    void deleteReview() throws Exception {
        Review review = new Review("1", "789 Pine St", "Test Location", "Mexican", 3, "Average", "Could be better");
        reviewService.createReview(review);

        mockMvc.perform(delete("/reviews/{id}", review.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createReviewWithInvalidData() throws Exception {
        Review invalidReview = new Review("1", "123 Main St", "Invalid Location", "Italian", -1, "Bad", "Invalid data");

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReview)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNonExistingReview() throws Exception {
        Review nonExistingReview = new Review("999", "Non-existing Address", "Non-existing Location", "Non-existing Cuisine", 1, "Poor", "Non-existing Review");

        mockMvc.perform(put("/reviews/{id}", nonExistingReview.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistingReview)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNonExistingReview() throws Exception {
        mockMvc.perform(delete("/reviews/{id}", "999"))
                .andExpect(status().isOk());
    }
}
