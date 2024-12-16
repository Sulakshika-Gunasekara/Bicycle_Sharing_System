package dev.mybike.mybike.repository.impl;

import dev.mybike.mybike.model.Weather;
import dev.mybike.mybike.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the WeatherRepository interface that retrieves weather data
 * from the OpenWeatherMap API. This class uses RestTemplate to fetch weather information
 * and constructs Weather objects based on the API responses.
 *
 * This repository retrieves current weather data as well as weather forecasts by interacting
 * with the OpenWeatherMap API endpoints. It integrates with a configuration-based setup
 * for the API key and base URL.
 *
 * Responsibilities:
 * - Fetches current weather information for a given location.
 * - Parses API responses to construct Weather domain objects.
 * - Incorporates spring-based dependency injection for configuration parameters.
 *
 * Fields:
 * - apiKey: The API key for accessing the OpenWeatherMap API.
 * - apiUrl: The base URL of the OpenWeatherMap API.
 * - restTemplate: A default RestTemplate instance used to send HTTP requests.
 *
 * Methods:
 * - findWeatherByLocation: Fetches current weather data for a specified location
 *   and returns it as a Weather object.
 * - findWeatherForecast: Overloaded methods to fetch weather forecast data for a location.
 *
 * Notes:
 * - The findWeatherByLocation method directly issues an HTTP GET request to the OpenWeatherMap API.
 * - Response parsing involves extracting details such as temperature, weather description,
 *   and timestamp, then mapping them into a Weather object.
 * - Using a default RestTemplate can be customized or replaced based on specific use-case requirements.
 */
@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Weather findWeatherByLocation(String location) {
        // Construct the URL for the OpenWeatherMap API call
        // Example: https://api.openweathermap.org/data/2.5/weather?q={location}&appid={apiKey}&units=metric
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, location, apiKey);

        // Call the OpenWeatherMap API
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Parse the response. The structure typically looks like this:
        // {
        //   "weather": [ { "description": "cloudy", ... } ],
        //   "main": { "temp": 22.5, ... },
        //   "dt": 1638576000 (epoch timestamp),
        //   "name": "Berlin",
        //   ...
        // }

        if (response == null || response.isEmpty()) {
            // Handle error or return a default Weather object
            return null;
        }

        // Extract fields
        String cityName = (String) response.get("name");

        // Weather description is within a list
        Object weatherList = response.get("weather");
        String description = "N/A";
        if (weatherList instanceof Iterable) {
            for (Object wObj : (Iterable<?>)weatherList) {
                if (wObj instanceof Map) {
                    Map<?,?> wMap = (Map<?,?>) wObj;
                    description = (String) wMap.get("description");
                    break; // Take the first description
                }
            }
        }

        Map<String, Object> mainMap = (Map<String, Object>) response.get("main");
        double temperature = mainMap != null && mainMap.get("temp") != null
                ? ((Number)mainMap.get("temp")).doubleValue()
                : 0.0;

        Number dt = (Number) response.get("dt");
        LocalDateTime dateTime = dt != null
                ? LocalDateTime.ofInstant(Instant.ofEpochSecond(dt.longValue()), ZoneId.systemDefault())
                : LocalDateTime.now();

        // Create and return the Weather object
        return new Weather(
                cityName != null ? cityName : location,
                description,
                temperature,
                dateTime
        );
    }

    @Override
    public Weather findWeatherForecast(String location, int days) {
        return null;
    }

    @Override
    public List<Weather> findWeatherForecast(String location) {
        return List.of();
    }
}