package com.example.foodfindsbackend.service;

import com.example.foodfindsbackend.model.Review;
import com.example.foodfindsbackend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Review review) {
        if (!reviewRepository.existsById(review.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        return reviewRepository.save(review);
    }


    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    public boolean reviewExists(String id) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        return existingReview.isPresent();
    }
}
