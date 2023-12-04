package com.example.foodfindsbackend.repository;

import com.example.foodfindsbackend.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
}
