package dev.mybike.mybike.service.impl;

import dev.mybike.mybike.model.Weather;
import dev.mybike.mybike.repository.WeatherRepository;
import dev.mybike.mybike.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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