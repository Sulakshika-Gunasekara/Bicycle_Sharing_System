package dev.mybike.mybike.repository;

import dev.mybike.mybike.model.Weather;

import java.util.List;

/**
 * Repository interface for managing Weather data.
 *
 * This interface defines methods for accessing and retrieving weather information
 * based on location and forecast duration. It is designed to provide weather data,
 * including current weather and future forecasts, and is intended to interact
 * with an external data source or API.
 *
 * Methods:
 * - findWeatherByLocation: Retrieves the current weather for a specific location.
 * - findWeatherForecast(String, int): Retrieves the weather forecast for a specific
 *   location and a given number of future days.
 * - findWeatherForecast(String): Retrieves a multi-day weather forecast for a specific
 *   location.
 *
 * Responsibilities:
 * - Serve as a data access layer for interacting with weather data sources.
 * - Provide weather information for various locations and forecast durations.
 */
public interface WeatherRepository {
    Weather findWeatherByLocation(String location);

    Weather findWeatherForecast(String location, int days);

    List<Weather> findWeatherForecast(String location);
}