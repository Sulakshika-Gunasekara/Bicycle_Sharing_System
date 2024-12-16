package dev.mybike.mybike.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents weather data for a specific location.
 * This class encapsulates basic weather information including the location name,
 * a textual description of the weather, temperature in Celsius, and the date and
 * time of the weather information.
 *
 * Fields:
 * - location: The name of the location for which the weather data is reported.
 * - description: A brief textual description of the weather conditions, e.g., "cloudy" or "misty".
 * - temperature: The temperature of the location in Celsius.
 * - dateTime: The date and time when the weather data was recorded or fetched.
 *
 * This class is typically used to interact with weather-related APIs and to store/retrieve
 * weather information in the system.
 */
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