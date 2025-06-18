package com.example.foodfindsbackend.controller;

import com.example.foodfindsbackend.model.Recommendation;
import com.example.foodfindsbackend.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(
        origins = {
                "http://localhost:4200",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody Recommendation recommendation) {
        try {
            // Validate recommendation data (e.g., using a validator method)
            // If data is invalid, return a bad request status
            if (!isValidRecommendationData(recommendation)) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(recommendationService.createRecommendation(recommendation));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Recommendation> getAllRecommendations() {
        return recommendationService.getAllRecommendations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recommendation> getRecommendationById(@PathVariable String id) {
        try {
            Recommendation recommendation = recommendationService.getRecommendationById(id);
            return ResponseEntity.ok(recommendation);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public Recommendation updateRecommendations(@PathVariable String id, @RequestBody Recommendation recommendation) {
        recommendation.setId(id);
        return recommendationService.updateRecommendation(recommendation);
    }

    @DeleteMapping("/{id}")
    public void deleteRecommendation(@PathVariable String id) {
        recommendationService.deleteRecommendation(id);
    }

    private boolean isValidRecommendationData(Recommendation recommendation) {
        return recommendation != null && recommendation.getCuisine() != null;
    }
}
