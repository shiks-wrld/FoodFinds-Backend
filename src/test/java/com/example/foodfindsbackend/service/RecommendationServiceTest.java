package com.example.foodfindsbackend.service;

import com.example.foodfindsbackend.model.Recommendation;
import com.example.foodfindsbackend.repository.RecommendationRepository;
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
class RecommendationServiceTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void createRecommendation() {
        Recommendation recommendationToSave = new Recommendation("1", "Italian", "Spicy", "Dinner", "Ristorante XYZ", "123 Main St");
        recommendationToSave.setCuisine("Italian");
        recommendationToSave.setTaste("Spicy");
        recommendationToSave.setMealType("Dinner");
        recommendationToSave.setRecommendation("Ristorante XYZ");
        recommendationToSave.setRecommendationAddress("123 Main St");

        when(recommendationRepository.save(any(Recommendation.class))).thenReturn(recommendationToSave);

        Recommendation savedRecommendation = recommendationService.createRecommendation(recommendationToSave);

        assertNotNull(savedRecommendation);
        assertEquals(recommendationToSave.getCuisine(), savedRecommendation.getCuisine());
        assertEquals(recommendationToSave.getRecommendation(), savedRecommendation.getRecommendation());
    }

    @Test
    void getAllRecommendations() {
        Recommendation recommendation1 = new Recommendation("1", "Italian", "Spicy", "Dinner", "Ristorante XYZ", "123 Main St");
        Recommendation recommendation2 = new Recommendation("2", "Mexican", "Savory", "Lunch", "Taqueria ABC", "456 Oak St");

        when(recommendationRepository.findAll()).thenReturn(Arrays.asList(recommendation1, recommendation2));

        List<Recommendation> recommendations = recommendationService.getAllRecommendations();

        assertEquals(2, recommendations.size());
        assertEquals(recommendation1, recommendations.get(0));
        assertEquals(recommendation2, recommendations.get(1));
    }

    @Test
    void updateRecommendation() {
        Recommendation existingRecommendation = new Recommendation("1", "Italian", "Spicy", "Dinner", "Ristorante XYZ", "123 Main St");
        Recommendation updatedRecommendation = new Recommendation("1", "Japanese", "Sweet", "Lunch", "Sushi Bar", "789 Pine St");

        when(recommendationRepository.save(updatedRecommendation)).thenReturn(updatedRecommendation);
        when(recommendationRepository.existsById("1")).thenReturn(true);
        when(recommendationRepository.findById("1")).thenReturn(Optional.of(existingRecommendation));

        Recommendation result = recommendationService.updateRecommendation(updatedRecommendation);

        assertEquals(updatedRecommendation, result);
    }

    @Test
    void deleteRecommendation() {
        String recommendationId = "1";

        doNothing().when(recommendationRepository).deleteById(recommendationId);
        when(recommendationRepository.existsById(recommendationId)).thenReturn(true);

        assertDoesNotThrow(() -> recommendationService.deleteRecommendation(recommendationId));

        verify(recommendationRepository, times(1)).deleteById(recommendationId);
    }
}