package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "feedback")
public class Feedback {

    @Id
    private String id;

    @JsonProperty("feedback")
    private String feedback;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("date")
    private String date;

    // Default Constructor
    public Feedback() {}

    // Constructor with JsonProperty to ensure proper deserialization
    @JsonCreator
    public Feedback(@JsonProperty("feedback") String feedback,
                    @JsonProperty("rating") int rating,
                    @JsonProperty("date") String date) {
        this.feedback = feedback;
        this.rating = rating;
        this.date = date;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}