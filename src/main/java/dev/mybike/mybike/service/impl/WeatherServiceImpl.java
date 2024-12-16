package dev.mybike.mybike.service.impl;

import dev.mybike.mybike.model.Weather;
import dev.mybike.mybike.repository.WeatherRepository;
import dev.mybike.mybike.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the WeatherService interface that provides weather-related
 * functionality such as retrieving current weather and weather forecasts for specific locations.
 *
 * This service interacts with the WeatherRepository to manage operations such as:
 * - Fetching the current weather data for a given location.
 * - Retrieving weather forecasts for a specific number of days for a given location.
 *
 * Responsibilities:
 * - Acts as a bridge between the business logic of the application and the data
 *   access layer for weather information.
 * - Applies any necessary business logic or transformations before retrieving or
 *   returning weather data.
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Weather getCurrentWeather(String location) {
        // Here you can add any business logic, caching, etc.
        return weatherRepository.findWeatherByLocation(location);
    }

    @Override
    public Weather getWeatherForecast(String location, int days) {
        return weatherRepository.findWeatherForecast(location, days);
    }
}