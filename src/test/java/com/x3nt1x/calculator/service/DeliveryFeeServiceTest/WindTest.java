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
public class WindTest
{
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private DeliveryFeeService deliveryService;

    private Weather weather;

    @BeforeEach
    public void setup()
    {
        this.weather = new Weather("", 0, "pärnu", 0.0, 0.0, "", LocalDateTime.now());
        doReturn(weather).when(weatherService).getWeather(any());
    }

    @Test
    public void noWindTest()
    {
        this.weather.setWind(0.0);

        assertThat(deliveryService.calculateDeliveryFee("pärnu", "car")).isEqualTo("3.0");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "bike")).isEqualTo("2.0");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "scooter")).isEqualTo("2.5");
    }

    @Test
    public void lightWindTest()
    {
        this.weather.setWind(15.0);

        assertThat(deliveryService.calculateDeliveryFee("pärnu", "car")).isEqualTo("3.0");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "bike")).isEqualTo("2.5");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "scooter")).isEqualTo("2.5");
    }

    @Test
    public void strongWindTest()
    {
        this.weather.setWind(25.0);

        assertThat(deliveryService.calculateDeliveryFee("pärnu", "car")).isEqualTo("3.0");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "bike")).isEqualTo("Usage of selected vehicle type is forbidden!");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "scooter")).isEqualTo("2.5");
    }
}