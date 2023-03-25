package com.x3nt1x.calculator.service;

import com.x3nt1x.calculator.entity.Weather;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryFeeService
{
    private final WeatherService weatherService;

    public String calculateDeliveryFee(String city, String vehicle)
    {
        return calculateDeliveryFee(city, vehicle, Optional.empty());
    }

    public String calculateDeliveryFee(String city, String vehicle, Optional<String> time)
    {
        city = city.toLowerCase();
        vehicle = vehicle.toLowerCase();

        var cities = new ArrayList<>(List.of("tallinn", "tartu", "pärnu"));
        var vehicles = new ArrayList<>(List.of("car", "scooter", "bike"));

        if (!cities.contains(city))
            return "Invalid city!";

        if (!vehicles.contains(vehicle))
            return "Invalid vehicle!";

        var weather = getWeather(city, time);
        if (weather == null)
            return "No weather data available or invalid time format!";

        if (!isVehicleAllowed(weather, vehicle))
            return "Usage of selected vehicle type is forbidden!";

        var deliveryFee = baseFee(city, vehicle) + extraFee(weather, vehicle);

        return String.valueOf(deliveryFee);
    }

    private Weather getWeather(String city, Optional<String> time)
    {
        if (time.isEmpty())
            return weatherService.getWeather(city);

        try
        {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
            var dateTime = LocalDateTime.parse(time.get(), formatter);

            return weatherService.getWeather(city, dateTime);
        }
        catch (Exception ignore)
        {
            return null;
        }
    }

    private boolean isVehicleAllowed(Weather weather, String vehicle)
    {
        if (vehicle.equals("car"))
            return true;

        if (weather.getPhenomenon().contains("Hail") ||
            weather.getPhenomenon().contains("Glaze") ||
            weather.getPhenomenon().contains("Thunder"))
            return false;

        if (vehicle.equals("bike") && weather.getWind() > 20.0)
            return false;

        return true;
    }

    private double baseFee(String city, String vehicle)
    {
        return switch (city)
        {
            case "tallinn" -> switch (vehicle)
            {
                case "car" -> 4.0;
                case "scooter" -> 3.5;
                default -> 3.0;
            };
            case "tartu" -> switch (vehicle)
            {
                case "car" -> 3.5;
                case "scooter" -> 3.0;
                default -> 2.5;
            };
            default -> switch (vehicle)
            {
                case "car" -> 3;
                case "scooter" -> 2.5;
                default -> 2.0;
            };
        };
    }

    private double extraFee(Weather weather, String vehicle)
    {
        var bonus = 0.0;

        if (vehicle.equals("car"))
            return bonus;

        if (weather.getTemperature() < -10.0)
            bonus += 1.0;
        else if (weather.getTemperature() < 0.0)
            bonus += 0.5;

        if (weather.getPhenomenon().contains("snow") || weather.getPhenomenon().contains("sleet"))
            bonus += 1.0;
        else if (weather.getPhenomenon().contains("rain"))
            bonus += 0.5;

        if (vehicle.equals("bike") && weather.getWind() > 10.0)
            bonus += 0.5;

        return bonus;
    }
}