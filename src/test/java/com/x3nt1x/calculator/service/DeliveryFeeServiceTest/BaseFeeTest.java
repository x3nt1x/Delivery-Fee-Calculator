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
public class BaseFeeTest
{
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private DeliveryFeeService deliveryService;

    private Weather weather;

    @BeforeEach
    public void setup()
    {
        this.weather = new Weather("", 0, "", 0.0, 0.0, "", LocalDateTime.now());
        doReturn(weather).when(weatherService).getWeather(any());
    }

    @Test
    public void tallinnBaseFeeTest()
    {
        this.weather.setCity("tallinn");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("3.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("3.5");
    }

    @Test
    public void tartuBaseFeeTest()
    {
        this.weather.setCity("tartu");

        assertThat(deliveryService.calculateDeliveryFee("tartu", "car")).isEqualTo("3.5");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "bike")).isEqualTo("2.5");
        assertThat(deliveryService.calculateDeliveryFee("tartu", "scooter")).isEqualTo("3.0");
    }

    @Test
    public void parnuBaseFeeTest()
    {
        this.weather.setCity("pärnu");

        assertThat(deliveryService.calculateDeliveryFee("pärnu", "car")).isEqualTo("3.0");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "bike")).isEqualTo("2.0");
        assertThat(deliveryService.calculateDeliveryFee("pärnu", "scooter")).isEqualTo("2.5");
    }
}