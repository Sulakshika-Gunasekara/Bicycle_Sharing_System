package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Weather;

public interface WeatherService {
    Weather getCurrentWeather(String location);

    Weather getWeatherForecast(String location, int days);
}
