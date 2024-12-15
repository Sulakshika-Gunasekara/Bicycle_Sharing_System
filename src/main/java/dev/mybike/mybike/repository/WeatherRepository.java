package dev.mybike.mybike.repository;

import dev.mybike.mybike.model.Weather;

import java.util.List;

public interface WeatherRepository {
    Weather findWeatherByLocation(String location);

    Weather findWeatherForecast(String location, int days);

    List<Weather> findWeatherForecast(String location);
}