package com.pet.project.weathertracker.models.api.version3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
    @JsonProperty("dt")
    private LocalDateTime dateTime;
    private LocalTime sunrise;
    private LocalTime sunset;
    private double temp;
    @JsonProperty("feels_like")
    private double feelsLike;
    private double humidity;
    private double clouds;
    @JsonProperty("wind_speed")
    private double windSpeed;
    private Weather weather;
}
