package com.x3nt1x.calculator.service.DeliveryFeeServiceTest;

import com.x3nt1x.calculator.entity.Weather;
import com.x3nt1x.calculator.service.WeatherService;
import com.x3nt1x.calculator.service.DeliveryFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class TemperatureTest
{
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private DeliveryFeeService deliveryService;

    private Weather weather;

    @BeforeEach
    public void setup()
    {
        this.weather = new Weather("", 0, "tartu", 0.0, 0.0, "", LocalDateTime.now());
        doReturn(weather).when(weatherService).getWeather(any());
    }

    @Test
    public void zeroDegreesTest()
    {
        this.weather.setTemperature(0.0);

        assertThat(deliveryService.calculateDeliveryFee("tartu", "car")).isEqualTo("3.5");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "bike")).isEqualTo("2.5");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "scooter")).isEqualTo("3.0");
    }

    @Test
    public void minusTenDegreesTest()
    {
        this.weather.setTemperature(-10.0);

        assertThat(deliveryService.calculateDeliveryFee("tartu", "car")).isEqualTo("3.5");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "bike")).isEqualTo("3.0");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "scooter")).isEqualTo("3.5");
    }

    @Test
    public void minusFifteenDegreesTest()
    {
        this.weather.setTemperature(-15.0);

        assertThat(deliveryService.calculateDeliveryFee("tartu", "car")).isEqualTo("3.5");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "bike")).isEqualTo("3.5");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "scooter")).isEqualTo("4.0");
    }
}