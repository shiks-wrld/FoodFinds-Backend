package com.example.foodfindsbackend.controller;

import com.example.foodfindsbackend.model.Recommendation;
import com.example.foodfindsbackend.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public Recommendation createRecommendation(@RequestBody Recommendation recommendation) {
        try {
            return recommendationService.createRecommendation(recommendation);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Recommendation> getAllRecommendations() {
        return recommendationService.getAllRecommendations();
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
}
