package com.example.foodfindsbackend.controller;

import com.example.foodfindsbackend.model.Review;
import com.example.foodfindsbackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Object> createReview(@RequestBody Review review) {
        if (!isValidReviewData(review)) {
            return ResponseEntity.badRequest().body("Invalid review data");
        }

        try {
            Review createdReview = reviewService.createReview(review);
            return ResponseEntity.status(HttpStatus.OK).body(createdReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable String id, @RequestBody Review review) {
        if (!reviewService.reviewExists(id)) {
            return ResponseEntity.notFound().build();
        }

        review.setId(id);
        try {
            Review updatedReview = reviewService.updateReview(review);
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
    }

    private boolean isValidReviewData(Review review) {
        if (review.getAddress() == null || review.getAddress().isEmpty()) {
            return false;
        }
        if (review.getLocationName() == null || review.getLocationName().isEmpty()) {
            return false;
        }
        if (review.getCuisine() == null || review.getCuisine().isEmpty()) {
            return false;
        }
        if (review.getRating() == null || review.getRating() < 0 || review.getRating() > 5) {
            return false;
        }
        if (review.getRating() < 3) {
            if (review.getComments() == null || review.getComments().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
