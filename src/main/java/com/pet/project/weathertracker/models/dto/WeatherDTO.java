package com.pet.project.weathertracker.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDTO {
    private String main;
    private String description;
    private String icon;
    private double temp;
    private double feelsLike;
    private double pressure;
    private double humidity;
    private double tempMin;
    private double tempMax;
    private double windSpeed;
    private int cloudiness;
    private OffsetDateTime dt;
    private OffsetDateTime sunrise;
    private OffsetDateTime sunset;
    private String country;
    private int timezone;
    private String name;
    private double lat;
    private double lon;
}
