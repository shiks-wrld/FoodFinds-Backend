package com.example.foodfindsbackend.repository;

import com.example.foodfindsbackend.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {

}
