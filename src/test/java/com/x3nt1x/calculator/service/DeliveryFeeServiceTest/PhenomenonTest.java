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
public class PhenomenonTest
{
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private DeliveryFeeService deliveryService;

    private Weather weather;

    @BeforeEach
    public void setup()
    {
        this.weather = new Weather("", 0, "tallinn", 0.0, 0.0, "", LocalDateTime.now());
        doReturn(weather).when(weatherService).getWeather(any());
    }

    @Test
    public void phenomenonClearTest()
    {
        this.weather.setPhenomenon("Clear");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("3.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("3.5");
    }

    @Test
    public void phenomenonSnowTest()
    {
        this.weather.setPhenomenon("Light snowfall");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("4.5");
    }

    @Test
    public void phenomenonSleetTest()
    {
        this.weather.setPhenomenon("Light sleet");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("4.5");
    }

    @Test
    public void phenomenonRainTest()
    {
        this.weather.setPhenomenon("Light rain");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("3.5");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("4.0");
    }

    @Test
    public void phenomenonGlazeTest()
    {
        this.weather.setPhenomenon("Glaze");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("Usage of selected vehicle type is forbidden!");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("Usage of selected vehicle type is forbidden!");
    }

    @Test
    public void phenomenonHailTest()
    {
        this.weather.setPhenomenon("Hail");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("Usage of selected vehicle type is forbidden!");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("Usage of selected vehicle type is forbidden!");
    }

    @Test
    public void phenomenonThunderTest()
    {
        this.weather.setPhenomenon("Thunderstorm");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("Usage of selected vehicle type is forbidden!");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("Usage of selected vehicle type is forbidden!");
    }
}