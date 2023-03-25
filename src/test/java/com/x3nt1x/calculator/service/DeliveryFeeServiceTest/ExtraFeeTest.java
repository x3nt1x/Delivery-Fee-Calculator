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
public class ExtraFeeTest
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
    public void temperatureAndWindTest()
    {
        this.weather.setWind(15.0);
        this.weather.setTemperature(-1.0);

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("4.0");
    }

    @Test
    public void temperatureAndPhenomenonTest()
    {
        this.weather.setTemperature(-15.0);
        this.weather.setPhenomenon("Moderate snowfall");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("5.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("5.5");
    }

    @Test
    public void allExtrasTest()
    {
        this.weather.setWind(15.0);
        this.weather.setTemperature(-5.0);
        this.weather.setPhenomenon("Light sleet");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("5.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("5.0");
    }

    @Test
    public void allExtrasAndBikeUseForbiddenTest()
    {
        this.weather.setWind(25.0);
        this.weather.setTemperature(-11.0);
        this.weather.setPhenomenon("Heavy rain");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("Usage of selected vehicle type is forbidden!");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("5.0");
    }

    @Test
    public void allExtrasOnlyCarAllowedTest()
    {
        this.weather.setWind(25.0);
        this.weather.setTemperature(-15.0);
        this.weather.setPhenomenon("Thunderstorm");

        assertThat(deliveryService.calculateDeliveryFee("tallinn", "car")).isEqualTo("4.0");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "bike")).isEqualTo("Usage of selected vehicle type is forbidden!");
        assertThat(deliveryService.calculateDeliveryFee("tallinn", "scooter")).isEqualTo("Usage of selected vehicle type is forbidden!");
    }
}