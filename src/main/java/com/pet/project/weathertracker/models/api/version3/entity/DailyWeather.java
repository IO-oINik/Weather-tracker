package com.pet.project.weathertracker.models.api.version3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
    @JsonProperty("dt")
    private LocalDateTime dateTime;
    private LocalTime sunrise;
    private LocalTime sunset;
    private LocalTime moonrise;
    private LocalTime moonset;
    private String summary;
    private Temperature temp;
    private Temperature feels_like;
    private double humidity;
    private double clouds;
    @JsonProperty("wind_speed")
    private double windSpeed;
    private Weather weather;
}
