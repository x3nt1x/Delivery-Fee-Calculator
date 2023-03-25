package com.x3nt1x.calculator.repository;

import com.x3nt1x.calculator.entity.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WeatherRepositoryTest
{
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private WeatherRepository weatherRepository;

    @BeforeEach
    public void setup()
    {
        testEntityManager.persist(new Weather("", 0, "tallinn", 0.0, 7.0, "Rain", LocalDateTime.of(2023, 3, 25, 13, 0)));
        testEntityManager.persist(new Weather("", 0, "tartu", 0.0, 3.0, "", LocalDateTime.of(2023, 3, 25, 14, 0)));
        testEntityManager.persist(new Weather("", 0, "tallinn", 0.0, 10.0, "Clear", LocalDateTime.of(2023, 3, 25, 15, 0)));
    }

    @Test
    public void findTopByCityOrderByTimestampDescTest()
    {
        var tallinn = weatherRepository.findTopByCityOrderByTimestampDesc("tallinn");
        assertThat(tallinn.getTemperature()).isEqualTo(10.0);
        assertThat(tallinn.getPhenomenon()).isEqualTo("Clear");

        var tartu = weatherRepository.findTopByCityOrderByTimestampDesc("tartu");
        assertThat(tartu.getTemperature()).isEqualTo(3.0);
    }

    @Test
    public void findTopByCityAndTimestampTest()
    {
        var tallinn = weatherRepository.findTopByCityAndTimestamp("tallinn", LocalDateTime.of(2023, 3, 25, 13, 0));
        assertThat(tallinn.getTemperature()).isEqualTo(7.0);
        assertThat(tallinn.getPhenomenon()).isEqualTo("Rain");
    }
}