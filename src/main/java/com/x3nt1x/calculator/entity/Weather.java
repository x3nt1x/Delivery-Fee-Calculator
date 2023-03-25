package com.x3nt1x.calculator.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "weather")
public class Weather
{
    @Id
    @Column
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String station;

    @Column
    private Integer wmo;

    @Column
    private String city;

    @Column
    private Double wind;

    @Column
    private Double temperature;

    @Column
    private String phenomenon;

    @Column
    private LocalDateTime timestamp;

    public Weather(String station, Integer wmo, String city, Double wind, Double temperature, String phenomenon, LocalDateTime timestamp)
    {
        this.station = station;
        this.wmo = wmo;
        this.city = city;
        this.wind = wind;
        this.temperature = temperature;
        this.phenomenon = phenomenon;
        this.timestamp = timestamp;
    }
}