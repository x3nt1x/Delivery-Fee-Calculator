package com.x3nt1x.calculator.repository;

import com.x3nt1x.calculator.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.time.LocalDateTime;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, UUID>
{
    Weather findTopByCityOrderByTimestampDesc(String city);

    Weather findTopByCityAndTimestamp(String city, LocalDateTime time);
}