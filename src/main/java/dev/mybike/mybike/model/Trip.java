package dev.mybike.mybike.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "trips")
public class Trip {
    @Id
    private String id;
    private String Ridername;
    private String destination;
    private Date startTime; // Change to Date
    private Date endTime;   // Change to Date

    // Getters and setters
}