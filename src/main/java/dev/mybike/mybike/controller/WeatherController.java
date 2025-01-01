package dev.mybike.mybike.controller;

import dev.mybike.mybike.model.Weather;
import dev.mybike.mybike.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for retrieving weather-related information.
 * This controller provides an endpoint for fetching the current weather
 * conditions and the current date/time for a specific location.
 */

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * GET /weather/{location}
     * Returns the current weather conditions (e.g., whether it's cloudy, misty,
     * etc.)
     * and the current date/time for the given location.
     */

    @GetMapping("/{location}")
    public Weather getWeatherForLocation(@PathVariable String location) {
        return weatherService.getCurrentWeather(location);
    }
}