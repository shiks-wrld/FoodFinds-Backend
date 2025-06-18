package com.example.foodfindsbackend.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "Review")
public class Review implements Serializable {
    @Id
    private String id;

    private String address;
    private String locationName;
    private String cuisine;
    private Integer rating;
    private String foodQuality;
    private String comments;

    public Review() {
    }

    public Review(String id, String address, String locationName, String cuisine, Integer rating, String foodQuality, String comments) {
        this.id = id;
        this.address = address;
        this.locationName = locationName;
        this.cuisine = cuisine;
        this.rating = rating;
        this.foodQuality = foodQuality;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFoodQuality() {
        return foodQuality;
    }

    public void setFoodQuality(String foodQuality) {
        this.foodQuality = foodQuality;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
