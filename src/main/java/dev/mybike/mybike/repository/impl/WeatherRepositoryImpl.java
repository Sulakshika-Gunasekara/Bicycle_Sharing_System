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