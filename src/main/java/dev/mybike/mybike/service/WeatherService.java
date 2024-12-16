package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Weather;

/**
 * Interface for managing weather-related operations.
 * This service provides functionalities to retrieve current weather
 * conditions and future weather forecasts for specified locations.
 *
 * Responsibilities:
 * - Retrieve the current weather conditions for a given location.
 * - Fetch weather forecasts for a specific number of days for a given location.
 */
public interface WeatherService {
    Weather getCurrentWeather(String location);

    Weather getWeatherForecast(String location, int days);
}
