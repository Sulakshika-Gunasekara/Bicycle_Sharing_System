package dev.mybike.mybike.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Weather {
    private String location;
    private String description; // e.g., "cloudy", "misty"
    private double temperature; // in Celsius, for example
    private LocalDateTime dateTime; // current date and time

    public Weather(String location, String description, double temperature, LocalDateTime dateTime) {
        this.location = location;
        this.description = description;
        this.temperature = temperature;
        this.dateTime = dateTime;
    }
    
    
}