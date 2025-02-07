package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "trips")
public class Trip {
    @Id
    private String _id;
    private String RiderId;
    private String bikeId;
    private String timeDuration;

}