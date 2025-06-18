package com.example.foodfindsbackend.service;

import com.example.foodfindsbackend.model.Review;
import com.example.foodfindsbackend.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void createReview() {
        Review reviewToSave = new Review();
        reviewToSave.setAddress("123 Main St");
        reviewToSave.setLocationName("Test Restaurant");
        reviewToSave.setCuisine("Italian");
        reviewToSave.setRating(4);
        reviewToSave.setFoodQuality("Good");
        reviewToSave.setComments("Enjoyed the meal!");

        when(reviewRepository.save(any(Review.class))).thenReturn(reviewToSave);

        Review savedReview = reviewService.createReview(reviewToSave);

        assertNotNull(savedReview);
        assertEquals(reviewToSave.getAddress(), savedReview.getAddress());
        assertEquals(reviewToSave.getRating(), savedReview.getRating());
        assertEquals(reviewToSave.getComments(), savedReview.getComments());
    }

    @Test
    void getAllReviews() {
        Review review1 = new Review("1", "456 Oak St", "Cafe ABC", "Mexican", 5, "Excellent", "Amazing experience!");
        Review review2 = new Review("2", "789 Pine St", "Sushi Bar", "Japanese", 3, "Average", "Could be better");

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));

        List<Review> reviews = reviewService.getAllReviews();

        assertEquals(2, reviews.size());
        assertEquals(review1, reviews.get(0));
        assertEquals(review2, reviews.get(1));
    }

    @Test
    void updateReview() {
        Review existingReview = new Review("1", "123 Main St", "Ristorante XYZ", "Italian", 4, "Good", "Enjoyed the meal!");
        Review updatedReview = new Review("1", "123 Main St", "Ristorante XYZ", "Italian", 5, "Excellent", "Fantastic experience!");

        when(reviewRepository.save(updatedReview)).thenReturn(updatedReview);
        when(reviewRepository.existsById("1")).thenReturn(true);
        when(reviewRepository.findById("1")).thenReturn(Optional.of(existingReview));

        Review result = reviewService.updateReview(updatedReview);

        assertEquals(updatedReview, result);
    }

    @Test
    void deleteReview() {
        String reviewId = "1";

        doNothing().when(reviewRepository).deleteById(reviewId);
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        assertDoesNotThrow(() -> reviewService.deleteReview(reviewId));

        verify(reviewRepository, times(1)).deleteById(reviewId);
    }
}
