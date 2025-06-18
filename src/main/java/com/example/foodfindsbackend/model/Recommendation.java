package com.example.foodfindsbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "Recommendation")
public class Recommendation implements Serializable {
    @Id
    private String id;

    private String cuisine;
    private String taste;
    private String mealType;
    private String recommendation;
    private String recommendationAddress;

    public Recommendation() {
    }

    public Recommendation(String id, String cuisine, String taste, String mealType, String recommendation, String recommendationAddress) {
        this.id = id;
        this.cuisine = cuisine;
        this.taste = taste;
        this.mealType = mealType;
        this.recommendation = recommendation;
        this.recommendationAddress = recommendationAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getRecommendationAddress() {
        return recommendationAddress;
    }

    public void setRecommendationAddress(String recommendationAddress) {
        this.recommendationAddress = recommendationAddress;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id='" + id + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", taste='" + taste + '\'' +
                ", mealType='" + mealType + '\'' +
                ", recommendation='" + recommendation + '\'' +
                ", recommendationAddress='" + recommendationAddress + '\'' +
                '}';
    }
}
