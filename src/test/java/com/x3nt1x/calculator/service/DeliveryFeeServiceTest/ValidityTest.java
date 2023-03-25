package com.x3nt1x.calculator.service.DeliveryFeeServiceTest;

import com.x3nt1x.calculator.service.WeatherService;
import com.x3nt1x.calculator.service.DeliveryFeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ValidityTest
{
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private DeliveryFeeService deliveryService;

    @Test
    public void invalidCityTest()
    {
        assertThat(deliveryService.calculateDeliveryFee("talin", "car")).isEqualTo("Invalid city!");
        assertThat(deliveryService.calculateDeliveryFee("Haapsalu", "car")).isEqualTo("Invalid city!");
    }

    @Test
    public void invalidVehicleTest()
    {
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "boat")).isEqualTo("Invalid vehicle!");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "plane")).isEqualTo("Invalid vehicle!");
    }

    @Test
    public void missingWeatherDataForGivenTimeTest()
    {
        var result = deliveryService.calculateDeliveryFee("tallinn", "car", Optional.of("2025-03-25-14"));
        assertThat(result).isEqualTo("No weather data available or invalid time format!");
    }

    @Test
    public void invalidTimeFormatTest()
    {
        var missingHour = deliveryService.calculateDeliveryFee("tallinn", "car", Optional.of("2023-03-25"));
        assertThat(missingHour).isEqualTo("No weather data available or invalid time format!");

        var wrongDelimiter = deliveryService.calculateDeliveryFee("tallinn", "car", Optional.of("2023-03-25.14"));
        assertThat(wrongDelimiter).isEqualTo("No weather data available or invalid time format!");

        var monthOutOfBounds = deliveryService.calculateDeliveryFee("tallinn", "car", Optional.of("2023-25-03-14"));
        assertThat(monthOutOfBounds).isEqualTo("No weather data available or invalid time format!");
    }
}