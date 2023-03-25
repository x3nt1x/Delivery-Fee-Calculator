package com.x3nt1x.calculator.service;

import com.x3nt1x.calculator.entity.Weather;
import com.x3nt1x.calculator.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class WeatherService
{
    private final WeatherRepository weatherRepository;

    public Weather getWeather(String city)
    {
        return weatherRepository.findTopByCityOrderByTimestampDesc(city);
    }

    public Weather getWeather(String city, LocalDateTime time)
    {
        return weatherRepository.findTopByCityAndTimestamp(city, time);
    }
}